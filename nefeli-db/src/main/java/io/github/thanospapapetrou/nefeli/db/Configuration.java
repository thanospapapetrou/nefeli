package io.github.thanospapapetrou.nefeli.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class Configuration {
    private static final String PERSISTENCE_UNIT = "nefeli";

    @ApplicationScoped
    @Produces
    public EntityManagerFactory getFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @Produces
    public EntityManager getManager(final EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

}
