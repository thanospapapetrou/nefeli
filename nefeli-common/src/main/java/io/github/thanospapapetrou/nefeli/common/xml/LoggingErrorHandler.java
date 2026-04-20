package io.github.thanospapapetrou.nefeli.common.xml;

import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@ApplicationScoped
public class LoggingErrorHandler implements ErrorHandler {
    private static final String ERROR = "Error parsing XML at %1$s, line %2$d, column %3$d: %4$s";
    private static final String FATAL_ERROR = "Fatal error parsing XML at %1$s, line %2$d, column %3$d: %4$s";
    private static final Logger LOGGER = Logger.getLogger(LoggingErrorHandler.class.getName());
    private static final String WARNING = "Warning parsing XML at %1$s, line %2$d, column %3$d: %4$s";

    @Override
    public void warning(final SAXParseException exception) {
        LOGGER.warning(String.format(WARNING, exception.getSystemId(), exception.getLineNumber(),
                exception.getColumnNumber(), exception.getMessage()));
    }

    @Override
    public void error(final SAXParseException exception) throws SAXException {
        LOGGER.warning(String.format(ERROR, exception.getSystemId(), exception.getLineNumber(),
                exception.getColumnNumber(), exception.getMessage()));
    }

    @Override
    public void fatalError(final SAXParseException exception) throws SAXException {
        LOGGER.warning(String.format(FATAL_ERROR, exception.getSystemId(), exception.getLineNumber(),
                exception.getColumnNumber(), exception.getMessage()));
        throw exception;
    }
}
