package io.github.thanospapapetrou.nefeli.harvester;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import io.github.thanospapapetrou.nefeli.common.Configuration;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;
import io.github.thanospapapetrou.nefeli.db.domain.Repository;
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhClient;

@ApplicationScoped
public class Harvester implements AutoCloseable, Runnable {
    private static final String ERROR_IDENTIFYING = "Error identifying repository %1$s";
    private static final String ERROR_LISTING_SETS = "Error listing sets of repository %1$s";
    private static final String ERROR_RETRIEVING = "Error retrieving repositories";
    private static final String ERROR_UPDATING = "Error updating repository %1$s";
    private static final Logger LOGGER = Logger.getLogger(Harvester.class.getName());
    private static final String MESSAGE_HARVESTER_STARTED = "Harvester started";
    private static final String MESSAGE_HARVESTER_STOPPED = "Harvester stopped";
    private static final String MESSAGE_IDENTIFIED = "Identified repository %1$s";
    private static final String MESSAGE_UPDATED_REPOSITORY = "Updated repository %1$s";

    private final RepositoryDao repositoryDao;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService workers;
    private final Duration period;

    @Inject
    public Harvester(final RepositoryDao repositoryDao,
            @Named("schedulerExecutor") final ScheduledExecutorService scheduler,
            @Named("workersExecutor") final ExecutorService workers,
            @Configuration.Property("nefeli.harvester.period") final Duration period) {
        this.repositoryDao = repositoryDao;
        this.scheduler = scheduler;
        this.workers = workers;
        this.period = period;
    }

    Harvester() {
        this(null, null, null, null);
    }

    public void run() {
        final long nanos = TimeUnit.SECONDS.toNanos(period.getSeconds()) + period.getNano();
        if (nanos > 0) {
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    repositoryDao.getRepositories().forEach(this::harvest);
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
                    .thenComposeAsync(nil -> this.listSets(client, null), workers);
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
        return step(client::identify, String.format(ERROR_IDENTIFYING, client))
                .thenComposeAsync(identify -> step(() -> {
                            repositoryDao.updateRepository(new Repository(
                                    identify.getBody().getBaseURL(),
                                    identify.getResponseDate(),
                                    identify.getBody().getRepositoryName(),
                                    identify.getBody().getAdminEmails(),
                                    identify.getBody().getEarliestDatestamp(),
                                    identify.getBody().getDeletedRecord(),
                                    identify.getBody().getGranularity(),
                                    identify.getBody().getCompressions()));
                            return null;
                        },
                        String.format(ERROR_UPDATING, identify.getBody().getBaseURL())), workers);
    }

    private CompletableFuture<String> listSets(final OaiPmhClient client, final String token) {
        return step(() -> (token == null) ? client.listSets() : client.listSets(token),
                String.format(ERROR_LISTING_SETS, client))
                .thenComposeAsync(listSets -> {
                    final CompletableFuture<String> batch = step(() -> {
                        listSets.getBody().getSets().forEach(set ->
                                LOGGER.info(String.format("Processing set: %1$s %2$s", set.getSetSpec(),
                                        set.getSetName()))); // TODO
                        return (listSets.getBody().getResumptionToken() == null) ? null
                                : listSets.getBody().getResumptionToken().getValue();
                    }, "Error updating sets");
                    return (listSets.getBody().getResumptionToken() == null) ? batch :
                            batch.thenComposeAsync(nextToken -> listSets(client, nextToken), workers);
                }, workers);
    }

    private CompletableFuture<Void> listMetadataFormats(final OaiPmhClient client) {
        return step(() -> client.listMetadataFormats(null), "Error listing metadata formats") // TODO
                .thenComposeAsync(listMetadataFormats -> step(() -> {
                    listMetadataFormats.getBody().getMetadataFormats().forEach(metadataFormat -> {
                        LOGGER.info(String.format("Processing metadata format: %1$s %2$s %3$s", // TODO
                                metadataFormat.getMetadataPrefix(), metadataFormat.getSchema(), metadataFormat.getMetadataNamespace()));
                    });
                    return null;
                }, "Error updating metadata formats"), workers); // TODO
    }

    private <T> CompletableFuture<T> step(final Callable<T> step, final String error) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        workers.submit(() -> {
            try {
                future.complete(step.call());
            } catch (final Exception e) {
                LOGGER.log(Level.WARNING, error, e);
                future.completeExceptionally(e);
            }
        });
        return future;
    }
}
