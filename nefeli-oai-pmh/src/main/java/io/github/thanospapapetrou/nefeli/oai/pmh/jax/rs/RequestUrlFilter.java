package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.io.IOException;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RequestUrlFilter implements ClientResponseFilter {
    @Override
    public void filter(final ClientRequestContext request, final ClientResponseContext response) throws IOException {
        response.getHeaders().add(getClass().getName(), request.getUri().toURL().toString());
    }
}
