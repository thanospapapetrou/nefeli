package io.github.thanospapapetrou.nefeli.db;

import jakarta.persistence.PersistenceException;

public class DaoException extends Exception {
    public DaoException(final String message, final PersistenceException e) {
        super(message, e);
    }
}
