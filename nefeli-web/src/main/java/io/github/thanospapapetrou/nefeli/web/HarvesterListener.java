package io.github.thanospapapetrou.nefeli.web;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import io.github.thanospapapetrou.nefeli.harvester.Harvester;

@WebListener
public class HarvesterListener implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        CDI.current().select(Harvester.class).get().run();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        CDI.current().select(Harvester.class).get().close();
    }
}
