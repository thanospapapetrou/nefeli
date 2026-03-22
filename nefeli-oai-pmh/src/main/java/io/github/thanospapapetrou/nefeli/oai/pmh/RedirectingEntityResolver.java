package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import javax.net.ssl.HttpsURLConnection;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

@ApplicationScoped
public class RedirectingEntityResolver implements EntityResolver {
    private static final String ERROR_RESOLVING = "Error resolving %1$s %2$s";

    @Override
    public InputSource resolveEntity(final String publicId, final String systemId) throws IOException {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URI(systemId).toURL().openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(HttpMethod.GET);
            connection.connect();
            if (Response.Status.fromStatusCode(connection.getResponseCode()).getFamily()
                    == Response.Status.Family.REDIRECTION) {
                return resolveEntity(publicId, connection.getHeaderField(HttpHeaders.LOCATION));
            }
            final InputSource source = new InputSource(systemId);
            source.setByteStream(connection.getInputStream());
            return source;
        } catch (final URISyntaxException e) {
            throw new IOException(String.format(ERROR_RESOLVING, publicId, systemId), e);
        }
    }
}

