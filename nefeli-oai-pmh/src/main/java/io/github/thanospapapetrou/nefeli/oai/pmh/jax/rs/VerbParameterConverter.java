package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.util.Arrays;

import jakarta.ws.rs.ext.ParamConverter;

import org.openarchives.oai._2.Verb;

public class VerbParameterConverter implements ParamConverter<Verb> {
    @Override
    public Verb fromString(final String string) {
        return Arrays.stream(Verb.values())
                .filter(verb -> verb.toString().equals(string))
                .findFirst().orElse(null);
    }

    @Override
    public String toString(final Verb verb) {
        return (verb == null) ? null : verb.toString();
    }
}
