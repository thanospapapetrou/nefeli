package io.github.thanospapapetrou.nefeli.harvester;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;

import io.github.thanospapapetrou.nefeli.db.Repository;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;

@ApplicationScoped
public class Harvester implements AutoCloseable, Runnable {
    private static final Logger LOGGER = Logger.getLogger(Harvester.class.getName());
    private static final String MESSAGE_HARVESTER_STARTED = "Harvester started";
    private static final String MESSAGE_HARVESTER_STOPPED = "Harvester stopped";
    private static final String THREAD_SCHEDULER = "Harvester Scheduler";
    private static final String THREAD__WORKER = "Harvester Worker %1$d";

    private final ScheduledExecutorService scheduler;
    private final ExecutorService workers;
    private final RepositoryDao repositoryDao;
    private final Duration period;

    public Harvester() {
        this(10, CDI.current().select(RepositoryDao.class).get(),
                Duration.parse("PT5S")); // TODO
    }

    private Harvester(final int threads, final RepositoryDao repositoryDao,
            final Duration period) {
        this(Executors.newSingleThreadScheduledExecutor(new DeaemonThreadFactory() {
                    @Override
                    public Thread newThread(@Nonnull final Runnable runnable) {
                        final Thread thread = super.newThread(runnable);
                        thread.setName(THREAD_SCHEDULER);
                        return thread;
                    }
                }), Executors.newFixedThreadPool(threads, new DeaemonThreadFactory() {
                    private static final AtomicInteger THREADS = new AtomicInteger(0);

                    @Override
                    public Thread newThread(@Nonnull final Runnable runnable) {
                        final Thread thread = super.newThread(runnable);
                        thread.setName(String.format(THREAD__WORKER, THREADS.getAndIncrement()));
                        return thread;
                    }
                }),
                repositoryDao, period);
    }

    private Harvester(final ScheduledExecutorService scheduler, final ExecutorService workers,
            final RepositoryDao repositoryDao, final Duration period) {
        this.scheduler = scheduler;
        this.workers = workers;
        this.repositoryDao = repositoryDao;
        this.period = period;
    }

    public void run() {
        final long nanos = TimeUnit.SECONDS.toNanos(period.getSeconds()) + period.getNano();
        if (nanos > 0) {
            scheduler.scheduleAtFixedRate(() -> {
                repositoryDao.getRepositories().forEach(this::harvest);
            }, 0L, nanos, TimeUnit.NANOSECONDS);
            LOGGER.info(MESSAGE_HARVESTER_STARTED);
        }
    }

    public void harvest(final Repository repository) {
        LOGGER.info("Harvesting " + repository.getUrl()); // TODO
    }

    @Override
    public void close() {
        scheduler.shutdownNow();
        workers.shutdownNow();
        scheduler.close();
        workers.close();
        LOGGER.info(MESSAGE_HARVESTER_STOPPED);
    }
}
