package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.util.List;
import java.util.stream.Collectors;

import org.openarchives.oai._2.OaiPmhError;

public class OaiPmhException extends Exception {
    private static final String DELIMITER = "\n";

    private final List<OaiPmhError> errors;

    public OaiPmhException(final List<OaiPmhError> errors) {
        this.errors = errors;
    }

    public List<OaiPmhError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return errors.stream()
                .map(OaiPmhError::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
