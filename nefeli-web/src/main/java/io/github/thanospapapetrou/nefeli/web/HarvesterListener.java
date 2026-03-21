package io.github.thanospapapetrou.nefeli.web;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import io.github.thanospapapetrou.nefeli.harvester.Harvester;

@WebListener
public class HarvesterListener implements ServletContextListener {
    private final Harvester harvester;

    public HarvesterListener() {
        this(CDI.current().select(Harvester.class).get());
    }

    private HarvesterListener(final Harvester harvester) {
        this.harvester = harvester;
    }

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        harvester.run();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        harvester.close(); // TODO properly close this
    }
}
