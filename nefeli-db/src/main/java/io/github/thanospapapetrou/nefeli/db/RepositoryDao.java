package io.github.thanospapapetrou.nefeli.db;

import java.util.List;

import io.github.thanospapapetrou.nefeli.db.domain.Repository;

public interface RepositoryDao {
    List<Repository> getRepositories() throws DaoException;

    boolean updateRepository(final Repository repository) throws DaoException;
}
