package io.github.thanospapapetrou.nefeli.harvester;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import io.github.thanospapapetrou.nefeli.common.Configuration;
import io.github.thanospapapetrou.nefeli.db.Repository;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;

@ApplicationScoped
public class Harvester implements AutoCloseable, Runnable {
    private static final Logger LOGGER = Logger.getLogger(Harvester.class.getName());
    private static final String MESSAGE_HARVESTER_STARTED = "Harvester started";
    private static final String MESSAGE_HARVESTER_STOPPED = "Harvester stopped";

    private final RepositoryDao repositoryDao;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService workers;
    private final Duration period;

    @Inject
    public Harvester(final RepositoryDao repositoryDao,
            @Named("schedulerExecutor") final ScheduledExecutorService scheduler,
            @Named("workersExecutor") final ExecutorService workers,
            @Configuration.Property("harvester.period") final Duration period) {
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
            scheduler.scheduleAtFixedRate(() -> repositoryDao.getRepositories().forEach(this::harvest), 0L,
                    nanos, TimeUnit.NANOSECONDS);
            LOGGER.info(MESSAGE_HARVESTER_STARTED);
        }
    }

    public void harvest(final Repository repository) {
        LOGGER.info("Harvesting " + repository.getUrl()); // TODO
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
}
