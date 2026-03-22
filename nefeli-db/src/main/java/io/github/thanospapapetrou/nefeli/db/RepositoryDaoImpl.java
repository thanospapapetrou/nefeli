package io.github.thanospapapetrou.nefeli.db;

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
    private static final String ERROR_UPDATING = "Error updating repository %1$s";
    private static final String PARAMETER_DELETED = "deleted";
    private static final String PARAMETER_EARLIEST = "earliest";
    private static final String PARAMETER_GRANULARITY = "granularity";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_UPDATED = "updated";
    private static final String PARAMETER_URL = "url";
    private static final String QUERY_GET_REPOSITORIES = """
            SELECT r
            FROM Repository r
            LEFT JOIN FETCH r.admins""";
    private static final String QUERY_UPDATE_REPOSITORY = """
            UPDATE Repository r SET
                r.updated = :updated,
                r.name = :name,
                r.earliest = :earliest,
                r.deleted = :deleted,
                r.granularity = :granularity
            WHERE (r.url = :url)""";

    private final EntityManagerFactory factory;

    @Inject
    public RepositoryDaoImpl(final EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Repository> getRepositories() throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                final List<Repository> repositories = manager.createQuery(QUERY_GET_REPOSITORIES, Repository.class)
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
    public boolean updateRepository(final Repository repository) throws DaoException {
        try (final EntityManager manager = factory.createEntityManager()) {
            final EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            try {
                final boolean update = manager.createQuery(QUERY_UPDATE_REPOSITORY)
                        .setParameter(PARAMETER_URL, repository.getUrl())
                        .setParameter(PARAMETER_UPDATED, repository.getUpdated())
                        .setParameter(PARAMETER_NAME, repository.getName())
                        .setParameter(PARAMETER_EARLIEST, repository.getEarliest())
                        .setParameter(PARAMETER_DELETED, repository.getDeleted())
                        .setParameter(PARAMETER_GRANULARITY, repository.getGranularity())
                        .executeUpdate() == 1;
                transaction.commit();
                return update;
            } catch (final Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_RETRIEVING, e);
        }
    }
}
