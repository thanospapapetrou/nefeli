package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import org.openarchives.oai._2.Verb;

@ApplicationScoped
@Provider
public class OaiPmhParameterConverterProvider implements ParamConverterProvider {
    private final InstantParameterConverter instantConverter;
    private final VerbParameterConverter verbConverter;

    @Inject
    public OaiPmhParameterConverterProvider(final InstantParameterConverter instantConverter,
            final VerbParameterConverter verbConverter) {
        this.instantConverter = instantConverter;
        this.verbConverter = verbConverter;
    }

    OaiPmhParameterConverterProvider() {
        this(null, null);
    }

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> clazz, final Type type, final Annotation[] annotations) {
        if (clazz == Instant.class) {
            return (ParamConverter<T>) instantConverter;
        } else if (clazz == Verb.class) {
            return (ParamConverter<T>) verbConverter;
        }
        return null;
    }
}
