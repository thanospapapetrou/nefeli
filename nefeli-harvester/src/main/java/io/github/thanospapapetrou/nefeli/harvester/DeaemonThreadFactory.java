package io.github.thanospapapetrou.nefeli.harvester;

import java.util.concurrent.ThreadFactory;

import jakarta.annotation.Nonnull;

public abstract class DeaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@Nonnull final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    }
}
