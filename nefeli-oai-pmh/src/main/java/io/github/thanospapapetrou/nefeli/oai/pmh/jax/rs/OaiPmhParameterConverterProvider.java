package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import org.openarchives.oai._2.SetSpec;
import org.openarchives.oai._2.Verb;

@ApplicationScoped
@Provider
public class OaiPmhParameterConverterProvider implements ParamConverterProvider {
    private final VerbParameterConverter verbConverter;
    private final UriParameterConverter uriConverter;
    private final InstantParameterConverter instantConverter;
    private final SetSpecParameterConverter setSpecConverter;

    @Inject
    public OaiPmhParameterConverterProvider(final VerbParameterConverter verbConverter,
            final UriParameterConverter uriConverter, final InstantParameterConverter instantConverter,
            final SetSpecParameterConverter setSpecConverter) {
        this.verbConverter = verbConverter;
        this.uriConverter = uriConverter;
        this.instantConverter = instantConverter;
        this.setSpecConverter = setSpecConverter;
    }

    OaiPmhParameterConverterProvider() {
        this(null, null, null, null);
    }

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> clazz, final Type type, final Annotation[] annotations) {
        if (clazz == Verb.class) {
            return (ParamConverter<T>) verbConverter;
        } else if (clazz == URI.class) {
            return (ParamConverter<T>) uriConverter;
        } else if (clazz == Instant.class) {
            return (ParamConverter<T>) instantConverter;
        } else if (clazz == SetSpec.class) {
            return (ParamConverter<T>) setSpecConverter;
        }
        return null;
    }
}
