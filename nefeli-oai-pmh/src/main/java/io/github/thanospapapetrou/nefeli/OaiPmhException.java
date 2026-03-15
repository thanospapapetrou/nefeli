package io.github.thanospapapetrou.nefeli;

import java.util.List;

import org.openarchives.oai._2.OaiPmhError;

public class OaiPmhException extends Exception {
    private final List<OaiPmhError> errors;

    public OaiPmhException(final List<OaiPmhError> errors) {
        this.errors = errors;
    }

    public List<OaiPmhError> getErrors() {
        return errors;
    }
}
