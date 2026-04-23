package io.github.thanospapapetrou.nefeli.common.jaxb;

import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;

import javax.xml.namespace.QName;

@ApplicationScoped
public class LoggingValidationEventHandler implements ValidationEventHandler {
    private static final String ERROR = "Error parsing XML at %1$s: %2$s";
    private static final String FATAL_ERROR = "Fatal error parsing XML at %1$s: %2$s";
    private static final Logger LOGGER = Logger.getLogger(LoggingValidationEventHandler.class.getName());
    private static final String WARNING = "Warning parsing XML at %1$s: %2$s";

    @Override
    public boolean handleEvent(final ValidationEvent event) {
        LOGGER.warning(String.format(switch (event.getSeverity()) {
                    case ValidationEvent.FATAL_ERROR -> FATAL_ERROR;
                    case ValidationEvent.ERROR -> ERROR;
                    default -> WARNING;
                },
                new QName(event.getLocator().getNode().getNamespaceURI(), event.getLocator().getNode().getLocalName()),
                event.getMessage()));
        return event.getSeverity() != ValidationEvent.FATAL_ERROR;
    }
}
