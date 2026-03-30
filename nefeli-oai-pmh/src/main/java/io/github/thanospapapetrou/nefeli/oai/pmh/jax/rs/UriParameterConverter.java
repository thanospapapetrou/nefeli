package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.net.URI;
import java.net.URISyntaxException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.ParamConverter;

@ApplicationScoped
public class UriParameterConverter implements ParamConverter<URI> {
    @Override
    public URI fromString(final String string) {
        try {
            return (string == null) ? null : new URI(string);
        } catch (final URISyntaxException e) {
            return null;
        }
    }

    @Override
    public String toString(final URI uri) {
        return (uri == null) ? null : uri.toString();
    }
}
