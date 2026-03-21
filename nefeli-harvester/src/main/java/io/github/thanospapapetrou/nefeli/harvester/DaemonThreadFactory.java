package io.github.thanospapapetrou.nefeli.harvester;

import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import jakarta.annotation.Nonnull;

public class DaemonThreadFactory implements ThreadFactory {
    private final Supplier<String> name;

    public DaemonThreadFactory(final Supplier<String> name) {
        this.name = name;
    }

    @Override
    public Thread newThread(@Nonnull final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(name.get());
        return thread;
    }
}
