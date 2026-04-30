package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ParamConverter;

import org.openarchives.oai._2.Granularity;

import io.github.thanospapapetrou.nefeli.common.cdi.Configuration;

@ApplicationScoped
public class InstantParameterConverter implements ParamConverter<Instant> {
    private final Granularity granularity;

    @Inject
    public InstantParameterConverter(
            @Configuration.Property("nefeli.oai-pmh.server.granularity") final Granularity granularity) {
        this.granularity = granularity;
    }

    InstantParameterConverter() {
        this(null);
    }

    @Override
    public Instant fromString(final String string) {
        try {
            return (string == null) ? null : granularity.parse(string);
        } catch (final DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString(final Instant instant) {
        return (instant == null) ? null : granularity.format(instant);
    }
}
