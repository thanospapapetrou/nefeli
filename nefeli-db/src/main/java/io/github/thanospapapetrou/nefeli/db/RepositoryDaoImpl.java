package io.github.thanospapapetrou.nefeli.db;

import java.net.URL;
import java.time.Instant;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import io.github.thanospapapetrou.nefeli.db.domain.Repository;

@ApplicationScoped
public class RepositoryDaoImpl implements RepositoryDao {
    private static final String ERROR_RETRIEVING = "Error retrieving repositories";
    private static final String ERROR_RETRIEVING_HARVEST = "Error retrieving repositories to harvest";
    private static final String ERROR_SETTING_ERROR = "Error setting repository error";
    private static final String QUERY_GET_HARVEST_REPOSITORIES = """
            SELECT r
            FROM Repository r
            WHERE r.error IS NULL
            ORDER BY r.updated ASC NULLS FIRST,
                r.url ASC""";
    private static final String QUERY_GET_REPOSITORIES = """
            SELECT r
            FROM Repository r
            ORDER BY r.url ASC""";

    private final EntityManagerFactory factory;

    @Inject
    public RepositoryDaoImpl(final EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Repository> getRepositories(final int offset, final int limit) throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                final List<Repository> repositories = manager.createQuery(QUERY_GET_REPOSITORIES, Repository.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList();
                transaction.commit();
                return repositories;
            } catch (final Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_RETRIEVING, e);
        }
    }

    @Override
    public List<Repository> getHarvestRepositories(final int offset, final int limit) throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                final List<Repository> repositories = manager.createQuery(QUERY_GET_HARVEST_REPOSITORIES, Repository.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList();
                transaction.commit();
                return repositories;
            } catch (final Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_RETRIEVING_HARVEST, e);
        }
    }

    @Override
    public void update(final Repository repository) throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                manager.merge(repository);
                transaction.commit();
            } catch (final Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_RETRIEVING, e);
        }
    }

    @Override
    public void setError(final URL url, final Instant updated, final String error) throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                final Repository existing = manager.find(Repository.class, url);
                manager.merge(new Repository(url, updated, error, existing.getName(), existing.getAdmins(),
                        existing.getEarliest(), existing.getDeleted(), existing.getGranularity(),
                        existing.getCompressions()));
                transaction.commit();
            } catch (final Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_SETTING_ERROR, e);
        }
    }
}
