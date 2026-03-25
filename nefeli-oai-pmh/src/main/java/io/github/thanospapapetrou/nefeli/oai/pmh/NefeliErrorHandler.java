package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@ApplicationScoped
public class NefeliErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(NefeliErrorHandler.class.getName());
    private static final String PARSING_ERROR = "Error parsing %s %s at line %d, column %d: %s";
    private static final String PARSING_FATAL_ERROR = "Fatal error parsing %s %s at line %d, column %d: %s";
    private static final String PARSING_WARNING = "Warning parsing %s %s at line %d, column %d: %s";

    @Override
    public void warning(final SAXParseException exception) {
        LOGGER.warning(String.format(PARSING_WARNING, exception.getPublicId(), exception.getSystemId(),
                exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));
    }

    @Override
    public void error(final SAXParseException exception) throws SAXException {
        LOGGER.warning(String.format(PARSING_ERROR, exception.getPublicId(), exception.getSystemId(),
                exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));
    }

    @Override
    public void fatalError(final SAXParseException exception) throws SAXException {
        LOGGER.log(Level.WARNING, String.format(PARSING_FATAL_ERROR, exception.getPublicId(), exception.getSystemId(),
                exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()), exception);
        throw exception;
    }
}
