package io.github.thanospapapetrou.nefeli.harvester;

import java.util.concurrent.ThreadFactory;

import jakarta.annotation.Nonnull;

public class HarvesterThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@Nonnull final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        // TODO set name?
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    }
}
