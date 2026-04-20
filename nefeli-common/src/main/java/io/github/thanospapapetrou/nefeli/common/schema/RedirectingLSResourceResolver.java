package io.github.thanospapapetrou.nefeli.common.schema;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.net.ssl.HttpsURLConnection;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

@ApplicationScoped
public class RedirectingLSResourceResolver implements LSResourceResolver {
    private static final String ERROR_RESOLVING = "Error resolving %1$s";
    private static final Logger LOGGER = Logger.getLogger(RedirectingLSResourceResolver.class.getName());

    @Override
    public LSInput resolveResource(final String type, final String namespace, final String publicId,
            final String systemId, final String base) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URI(systemId).toURL().openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(HttpMethod.GET);
            connection.connect();
            if (Response.Status.fromStatusCode(connection.getResponseCode()).getFamily()
                    == Response.Status.Family.REDIRECTION) {
                return resolveResource(type, namespace, publicId, connection.getHeaderField(HttpHeaders.LOCATION),
                        base);
            }
            try (final InputStream stream = connection.getInputStream()) {
                return new ReadOnlyLSInput(stream.readAllBytes(), Charset.forName(
                        MediaType.valueOf(connection.getContentType()).getParameters()
                                .get(MediaType.CHARSET_PARAMETER)), new URI(systemId).toURL());
            }
        } catch (final URISyntaxException | IOException e) {
            LOGGER.log(Level.WARNING, String.format(ERROR_RESOLVING, systemId), e);
            return null;
        }
    }
}
