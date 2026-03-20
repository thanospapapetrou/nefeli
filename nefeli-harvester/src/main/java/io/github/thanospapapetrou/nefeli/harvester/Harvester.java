package io.github.thanospapapetrou.nefeli.harvester;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;

import io.github.thanospapapetrou.nefeli.db.Repository;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;

@ApplicationScoped
public class Harvester implements AutoCloseable, Runnable {
    private static final Logger LOGGER = Logger.getLogger(Harvester.class.getName());
    private static final String MESSAGE_HARVESTER_STARTED = "Harvester started";
    private static final String MESSAGE_HARVESTER_STOPPED = "Harvester stopped";

    private final ScheduledExecutorService scheduled;
    private final ExecutorService fixed;
    private final RepositoryDao repositoryDao;
    private final Duration period;

    public Harvester() {
        this(10, new HarvesterThreadFactory(), CDI.current().select(RepositoryDao.class).get(),
                Duration.parse("PT5S")); // TODO
    }

    private Harvester(final int threads, final ThreadFactory factory, final RepositoryDao repositoryDao,
            final Duration period) {
        this(Executors.newSingleThreadScheduledExecutor(factory), Executors.newFixedThreadPool(threads, factory),
                repositoryDao, period);
    }

    private Harvester(final ScheduledExecutorService scheduled, final ExecutorService fixed,
            final RepositoryDao repositoryDao, final Duration period) {
        this.scheduled = scheduled;
        this.fixed = fixed;
        this.repositoryDao = repositoryDao;
        this.period = period;
    }

    public void run() {
        scheduled.scheduleAtFixedRate(() -> {
            repositoryDao.getRepositories().forEach(this::harvest);
        }, 0L, period.getSeconds(), TimeUnit.SECONDS);
        LOGGER.info(MESSAGE_HARVESTER_STARTED);
    }

    public void harvest(final Repository repository) {
        LOGGER.info("Harvesting " + repository.getUrl());
    }

    @Override
    public void close() {
        scheduled.shutdownNow();
        fixed.shutdownNow();
        scheduled.close();
        fixed.close();
        LOGGER.info(MESSAGE_HARVESTER_STOPPED);
    }
}
