package io.github.thanospapapetrou.nefeli.db;

import java.net.URL;
import java.time.Instant;
import java.util.List;

import io.github.thanospapapetrou.nefeli.db.domain.Repository;

public interface RepositoryDao {
    List<Repository> getRepositories(final int offset, final int limit) throws DaoException;
    List<Repository> getHarvestRepositories(final int offset, final int limit) throws DaoException;
    void update(final Repository repository) throws DaoException;
    void setError(final URL url, final Instant updated, final String error) throws DaoException;
}
