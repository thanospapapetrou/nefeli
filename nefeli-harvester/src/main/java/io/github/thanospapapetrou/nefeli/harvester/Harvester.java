package io.github.thanospapapetrou.nefeli.harvester;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;

import javax.net.ssl.SSLHandshakeException;

import org.openarchives.oai._2.OaiPmhError;
import org.openarchives.oai._2.OaiPmhErrorCode;

import io.github.thanospapapetrou.nefeli.common.Configuration;
import io.github.thanospapapetrou.nefeli.db.DaoException;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;
import io.github.thanospapapetrou.nefeli.db.domain.Repository;
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhClient;
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhException;
import io.github.thanospapapetrou.nefeli.oai.pmh.UnsupportedMediaTypeException;

@ApplicationScoped
public class Harvester implements AutoCloseable, Runnable {
    private static final String DELIMITER = "\n";
    private static final String ERROR_HTTP = "HTTP %1$d";
    private static final String ERROR_IDENTIFYING = "Error identifying repository %1$s";
    private static final String ERROR_LISTING_SETS = "Error listing sets of repository %1$s";
    private static final String ERROR_REDIRECT = "HTTP %1$d %2$s";
    private static final String ERROR_RETRIEVING = "Error retrieving repositories";
    private static final String ERROR_SETTING_ERROR = "Error setting error for repository %1$s";
    private static final String ERROR_UPDATING = "Error updating repository %1$s";
    private static final String FORMAT_ERROR_UNRECOVERABLE = "%1$s (%2$s)";
    private static final Logger LOGGER = Logger.getLogger(Harvester.class.getName());
    private static final String MESSAGE_HARVESTER_STARTED = "Harvester started";
    private static final String MESSAGE_HARVESTER_STOPPED = "Harvester stopped";

    private final RepositoryDao repositoryDao;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService workers;
    private final Clock clock;
    private final Duration period;
    private final int batch;

    @Inject
    public Harvester(final RepositoryDao repositoryDao,
            @Named("schedulerExecutor") final ScheduledExecutorService scheduler,
            @Named("workersExecutor") final ExecutorService workers, final Clock clock,
            @Configuration.Property("nefeli.harvester.period") final Duration period,
            @Configuration.Property("nefeli.harvester.batch") final int batch) {
        this.repositoryDao = repositoryDao;
        this.scheduler = scheduler;
        this.workers = workers;
        this.clock = clock;
        this.period = period;
        this.batch = batch;
    }

    Harvester() {
        this(null, null, null, null, null, 0);
    }

    public void run() {
        final long nanos = TimeUnit.SECONDS.toNanos(period.getSeconds()) + period.getNano();
        if (nanos > 0) {
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    int offset = 0;
                    List<Repository> repositories;
                    do {
                        repositories = repositoryDao.getHarvestRepositories(offset, batch);
                        repositories.forEach(this::harvest);
                        offset += batch;
                    } while (repositories.size() == batch);
                } catch (final Exception e) {
                    LOGGER.log(Level.WARNING, ERROR_RETRIEVING, e);
                }
            }, 0L, nanos, TimeUnit.NANOSECONDS);
            LOGGER.info(MESSAGE_HARVESTER_STARTED);
        }
    }

    public void harvest(final Repository repository) {
        try {
            final OaiPmhClient client = new OaiPmhClient(repository.getUrl());
            CompletableFuture.completedFuture(client)
                    .thenComposeAsync(this::identify, workers)
            // TODO .thenComposeAsync(nil -> this.listSets(client, null), workers)
            ;
        } catch (final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PreDestroy
    public void close() {
        scheduler.shutdownNow();
        workers.shutdownNow();
        scheduler.close();
        workers.close();
        LOGGER.info(MESSAGE_HARVESTER_STOPPED);
    }

    private CompletableFuture<Void> identify(final OaiPmhClient client) {
        return step(client::identify, client, String.format(ERROR_IDENTIFYING, client.getUrl()))
                .thenComposeAsync(identify -> step(() -> {
                    repositoryDao.update(
                            new Repository(identify.getBody().getBaseUrl(), identify.getResponseDate(), null,
                                    identify.getBody().getRepositoryName(), identify.getBody().getAdminEmails(),
                                    identify.getBody().getEarliestDatestamp(), identify.getBody().getDeletedRecord(),
                                    identify.getBody().getGranularity(), identify.getBody().getCompressions()));
                    return null;
                }, client, String.format(ERROR_UPDATING, identify.getBody().getBaseUrl())), workers);
    }

    private CompletableFuture<String> listSets(final OaiPmhClient client, final String token) {
        return step(() -> (token == null) ? client.listSets() : client.listSets(token), client,
                String.format(ERROR_LISTING_SETS, client.getUrl()))
                .thenComposeAsync(listSets -> {
                    final CompletableFuture<String> batch = step(() -> {
                        listSets.getBody().getSets().forEach(set ->
                                LOGGER.info(String.format("Processing set: %1$s %2$s", set.getSetSpec(),
                                        set.getSetName()))); // TODO
                        return (listSets.getBody().getResumptionToken() == null) ? null
                                : listSets.getBody().getResumptionToken().getValue();
                    }, client, "Error updating sets");
                    return (listSets.getBody().getResumptionToken() == null) ? batch :
                            batch.thenComposeAsync(nextToken -> listSets(client, nextToken), workers);
                }, workers);
    }

    private CompletableFuture<Void> listMetadataFormats(final OaiPmhClient client) {
        return step(() -> client.listMetadataFormats(null), client, "Error listing metadata formats") // TODO
                .thenComposeAsync(listMetadataFormats -> step(() -> {
                    listMetadataFormats.getBody().getMetadataFormats().forEach(metadataFormat -> {
                        LOGGER.info(String.format("Processing metadata format: %1$s %2$s %3$s", // TODO
                                metadataFormat.getMetadataPrefix(), metadataFormat.getSchema(), metadataFormat.getMetadataNamespace()));
                    });
                    return null;
                }, client, "Error updating metadata formats"), workers); // TODO
    }

    private <T> CompletableFuture<T> step(final Callable<T> step, final OaiPmhClient client, final String error) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        workers.submit(() -> {
            try {
                future.complete(step.call());
            } catch (final Exception e) {
                final String unrecoverable = getUnrecoverableError(e);
                if (unrecoverable != null) {
                    LOGGER.log(Level.WARNING, String.format(FORMAT_ERROR_UNRECOVERABLE, error, unrecoverable));
                    setRepositoryError(client.getUrl(), unrecoverable);
                } else {
                    LOGGER.log(Level.WARNING, error, e);
                    final StringWriter stackTrace = new StringWriter();
                    e.printStackTrace(new PrintWriter(stackTrace, true));
                }
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    public String getUnrecoverableError(final Exception e) {
        if (e instanceof OaiPmhException o) {
            return o.getErrors().stream()
                    .map(OaiPmhError::getCode)
                    .map(OaiPmhErrorCode::toString)
                    .collect(Collectors.joining(DELIMITER));
        } else if (e instanceof WebApplicationException w) {
            return String.format(ERROR_HTTP, w.getResponse().getStatus());
        } else if (e instanceof UnsupportedMediaTypeException u) {
            return u.getMediaType().toString();
        } else if (e instanceof HttpRetryException r) {
            return String.format(ERROR_REDIRECT, r.responseCode(), r.getLocation());
        } else if ((e instanceof IOException) && (e.getCause() instanceof ProcessingException)
                && ((e.getCause().getCause() instanceof UnknownHostException)
                || (e.getCause().getCause() instanceof ConnectException)
                || (e.getCause().getCause() instanceof SocketException)
                || (e.getCause().getCause() instanceof SSLHandshakeException))) {
            return e.getCause().getCause().getClass().getSimpleName();
        }
        return null;
    }

    private void setRepositoryError(final URL url, final String error) {
        try {
            repositoryDao.update(new Repository(url, clock.instant(), error, null, null, null, null, null, null));
        } catch (final DaoException e) {
            LOGGER.warning(String.format(ERROR_SETTING_ERROR, url));
        }
    }
}
