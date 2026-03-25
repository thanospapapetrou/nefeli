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
    private static final String QUERY_GET_REPOSITORIES = """
            SELECT r
            FROM Repository r
            ORDER BY r.updated ASC NULLS FIRST,
                r.url ASC""";

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
    public void updateRepository(final Repository repository) throws DaoException {
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
}
