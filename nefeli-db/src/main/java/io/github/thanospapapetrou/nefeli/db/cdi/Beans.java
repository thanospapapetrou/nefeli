package io.github.thanospapapetrou.nefeli.db.cdi;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import io.github.thanospapapetrou.nefeli.common.Configuration;

@ApplicationScoped
public class Beans {
    private static final String PERSISTENCE_UNIT = "nefeli";
    private static final String PROPERTY_DRIVER = "jakarta.persistence.jdbc.driver";
    private static final String PROPERTY_URL = "jakarta.persistence.jdbc.url";
    private static final String PROPERTY_USER = "jakarta.persistence.jdbc.user";
    private static final String PROPERTY_PASSWORD = "jakarta.persistence.jdbc.password";

    @ApplicationScoped
    @Produces
    public EntityManagerFactory getFactory(final Map<String, String> properties) {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
    }

    public void close(@Disposes final EntityManagerFactory factory) {
        factory.close();
    }

    @ApplicationScoped
    @Produces
    public Map<String, String> getProperties(
            @Configuration.Property("nefeli.db.jdbc.driver") final String driver, // Class<?> cannot be injected
            @Configuration.Property("nefeli.db.jdbc.url") final String url, // Java doesn't support jdbc://... URLs
            @Configuration.Property("nefeli.db.jdbc.user") final String user,
            @Configuration.Property("nefeli.db.jdbc.password") final String password) {
        return Map.of(
                PROPERTY_DRIVER, driver,
                PROPERTY_URL, url,
                PROPERTY_USER, user,
                PROPERTY_PASSWORD, password);
    }
}
