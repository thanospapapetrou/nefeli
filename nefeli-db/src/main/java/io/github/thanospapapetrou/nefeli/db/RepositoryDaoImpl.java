package io.github.thanospapapetrou.nefeli.db;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import io.github.thanospapapetrou.nefeli.db.domain.Repository;

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

    private final EntityManager manager;
    private final TypedQuery<Repository> getRepositories;
    private final Query updateRepository;

    @Inject
    public RepositoryDaoImpl(final EntityManager manager) {
        this(manager, manager.createQuery(QUERY_GET_REPOSITORIES, Repository.class), manager.createQuery(
                QUERY_UPDATE_REPOSITORY));
    }

    private RepositoryDaoImpl(final EntityManager manager, final TypedQuery<Repository> getRepositories,
            final Query updateRepository) {
        this.manager = manager;
        this.getRepositories = getRepositories;
        this.updateRepository = updateRepository;
    }

    @Override
    public synchronized List<Repository> getRepositories() throws DaoException {
        try {
            return getRepositories.getResultList();
        } catch (final PersistenceException e) {
            throw new DaoException(ERROR_RETRIEVING, e);
        }
    }

    @Override
    public synchronized boolean updateRepository(final Repository repository) throws DaoException {
        final EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            final boolean update = updateRepository
                    .setParameter(PARAMETER_URL, repository.getUrl())
                    .setParameter(PARAMETER_UPDATED, repository.getUpdated())
                    .setParameter(PARAMETER_NAME, repository.getName())
                    .setParameter(PARAMETER_EARLIEST, repository.getEarliest())
                    .setParameter(PARAMETER_DELETED, repository.getDeleted())
                    .setParameter(PARAMETER_GRANULARITY, repository.getGranularity())
                    .executeUpdate() == 1;
            // TODO update admins
            transaction.commit();
            return update;
        } catch (final PersistenceException e) {
            transaction.rollback();
            throw new DaoException(String.format(ERROR_UPDATING, repository), e);
        }
    }
}
