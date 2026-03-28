package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.time.Instant;

import jakarta.ws.rs.ext.ParamConverter;

import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;

public class InstantParameterConverter implements ParamConverter<Instant> {
    private final InstantStringAdapter adapter;

    public InstantParameterConverter(final InstantStringAdapter adapter) {
        this.adapter = adapter; // TODO decide who delegates to whom
    }

    @Override
    public Instant fromString(final String string) {
        return adapter.unmarshal(string);
    }

    @Override
    public String toString(final Instant instant) {
        return adapter.marshal(instant);
    }
}
