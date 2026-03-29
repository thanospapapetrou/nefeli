package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ParamConverter;

import org.openarchives.oai._2.Granularity;

import io.github.thanospapapetrou.nefeli.common.Configuration;

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
        return granularity.parse(string);
    }

    @Override
    public String toString(final Instant instant) {
        return granularity.format(instant);
    }
}
