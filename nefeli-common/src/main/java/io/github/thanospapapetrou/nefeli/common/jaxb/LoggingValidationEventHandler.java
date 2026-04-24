package io.github.thanospapapetrou.nefeli.common.jaxb;

import java.util.Map;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;

@ApplicationScoped
public class LoggingValidationEventHandler implements ValidationEventHandler {
    private static final Logger LOGGER = Logger.getLogger(LoggingValidationEventHandler.class.getName());
    private static final Map<Integer, String> MESSAGES = Map.of(
            ValidationEvent.WARNING, "Warning parsing XML: %1$s",
            ValidationEvent.ERROR, "Error parsing XML: %1$s"
    );

    @Override
    public boolean handleEvent(final ValidationEvent event) {
        if (event.getSeverity() != ValidationEvent.FATAL_ERROR) {
            LOGGER.warning(String.format(MESSAGES.get(event.getSeverity()), event.getMessage()));
        }
        return event.getSeverity() != ValidationEvent.FATAL_ERROR;
    }
}
