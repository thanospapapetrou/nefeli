package io.github.thanospapapetrou.nefeli.db.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class Beans {
    private static final String PERSISTENCE_UNIT = "nefeli";

    @ApplicationScoped
    @Produces
    public EntityManagerFactory getFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    public void close(@Disposes final EntityManagerFactory factory) {
        factory.close();
    }
}
