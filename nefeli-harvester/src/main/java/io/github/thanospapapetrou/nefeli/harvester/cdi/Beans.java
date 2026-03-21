package io.github.thanospapapetrou.nefeli.harvester.cdi;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import io.github.thanospapapetrou.nefeli.harvester.DaemonThreadFactory;

@ApplicationScoped
public class Beans {
    private static final String THREAD_SCHEDULER = "Harvester Scheduler";
    private static final String THREAD__WORKER = "Harvester Worker %1$d";

    @ApplicationScoped
    @Named("schedulerExecutor")
    @Produces
    public ScheduledExecutorService getSchedulerExecutor(@Named("schedulerThreadFactory") final ThreadFactory factory) {
        return Executors.newSingleThreadScheduledExecutor(factory);
    }

    @ApplicationScoped
    @Named("workersExecutor")
    @Produces
    public ExecutorService getWorkersExecutor(@Named("workersThreadFactory") final ThreadFactory factory) {
        final int threads = 10; // TODO
        return Executors.newFixedThreadPool(threads, factory);
    }

    @Produces
    public Duration getDuration() {
        return Duration.parse("PT5S"); // TODO
    }

    @ApplicationScoped
    @Named("schedulerThreadFactory")
    @Produces
    public ThreadFactory getSchedulerThreadFactory() {
        return new DaemonThreadFactory(() -> THREAD_SCHEDULER);
    }

    @ApplicationScoped
    @Named("workersThreadFactory")
    @Produces
    public ThreadFactory getWorkerThreadFactory() {
        final AtomicInteger threads = new AtomicInteger(0);
        return new DaemonThreadFactory(() -> String.format(THREAD__WORKER, threads.getAndIncrement()));
    }
}
