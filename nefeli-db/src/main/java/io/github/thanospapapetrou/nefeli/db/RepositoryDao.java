package io.github.thanospapapetrou.nefeli.db;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class RepositoryDao {
    private final EntityManager manager;

    @Inject
    public RepositoryDao(final EntityManager manager) {
        this.manager = manager;
    }

    public List<Repository> getRepositories() {
        Query query = manager.createQuery("SELECT r from Repository r");
        return (List<Repository>) query.getResultList();
    }
}
