package io.github.thanospapapetrou.nefeli.db;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class RepositoryDao {
    private static final String GET_REPOSITORIES = "SELECT r from Repository r";

    private final EntityManager manager;
    private final TypedQuery<Repository> getRepositories;

    @Inject
    public RepositoryDao(final EntityManager manager) {
        this(manager, manager.createQuery(GET_REPOSITORIES, Repository.class));
    }

    private RepositoryDao(final EntityManager manager, final TypedQuery<Repository> getRepositories) {
        this.manager = manager;
        this.getRepositories = getRepositories;
    }

    public List<Repository> getRepositories() {
        return getRepositories.getResultList();
    }
}
