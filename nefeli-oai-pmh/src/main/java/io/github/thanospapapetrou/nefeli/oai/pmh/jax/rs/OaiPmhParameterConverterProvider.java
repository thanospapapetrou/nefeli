package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Instant;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import org.openarchives.oai._2.Verb;

import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;

@Provider
public class OaiPmhParameterConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> clazz, final Type type, final Annotation[] annotations) {
        if (clazz == Instant.class) {
            return (ParamConverter<T>) new InstantParameterConverter(new InstantStringAdapter(null));
        } else if (clazz == Verb.class) {
            return (ParamConverter<T>) new VerbParameterConverter();
        }
        return null;
    }
}
