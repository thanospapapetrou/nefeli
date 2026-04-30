package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.openarchives.oai._2.GetRecord;
import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListRecords;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.SetSpec;
import org.openarchives.oai._2.Verb;

import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhReader;

public class OaiPmhClient implements OaiPmh, AutoCloseable {
    private static final String ERROR_SENDING_REQUEST = "Error sending OAI-PMH request";
    private static final String ERROR_REDIRECTING = "Error redirecting to %1$s";
    private static final String HEADER_FROM = "From";

    private final Client client;
    private final WebTarget target;

    public OaiPmhClient(final URL url) throws URISyntaxException {
        this(CDI.current().select(Client.class).get(), url, CDI.current().select(OaiPmhReader.class).get());
    }

    private OaiPmhClient(final Client client, final URL url, final OaiPmhReader<?> reader) throws URISyntaxException {
        this(client, client.target(url.toURI()));
        this.target.register(reader);
    }

    private OaiPmhClient(final Client client, final WebTarget target) {
        this.client = client;
        this.target = target;
    }

    public URL getUrl() {
        try {
            return target.getUri().toURL();
        } catch (final MalformedURLException e) {
            throw new IllegalStateException(e); // TODO
        }
    }

    @Override
    public OaiPmhResponse<Identify> identify() throws IOException, OaiPmhException, RetryAfterException,
            WebApplicationException {
        final OaiPmhResponse<Identify> identify = request(Map.of(
                ARGUMENT_VERB, Verb.IDENTIFY
        ));
        if (identify.getBody() != null) {
            this.target.getConfiguration().getInstances().stream()
                    .filter(instance -> instance instanceof OaiPmhReader)
                    .map(instance -> (OaiPmhReader<?>) instance)
                    .forEach(reader -> reader.setGranularity(identify.getBody().getGranularity()));
        }
        return identify;
    }

    @Override
    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(
                ARGUMENT_VERB, Verb.LIST_METADATA_FORMATS,
                ARGUMENT_IDENTIFIER, identifier
        ));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets()
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(ARGUMENT_VERB, Verb.LIST_SETS));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(
                ARGUMENT_VERB, Verb.LIST_SETS,
                ARGUMENT_RESUMPTION_TOKEN, resumptionToken
        ));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put(ARGUMENT_VERB, Verb.LIST_IDENTIFIERS);
        arguments.put(ARGUMENT_METADATA_PREFIX, metadataPrefix);
        arguments.put(ARGUMENT_FROM, from);
        arguments.put(ARGUMENT_UNTIL, until);
        arguments.put(ARGUMENT_SET, set);
        return request(arguments);
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(
                ARGUMENT_VERB, Verb.LIST_IDENTIFIERS,
                ARGUMENT_RESUMPTION_TOKEN, resumptionToken
        ));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put(ARGUMENT_VERB, Verb.LIST_RECORDS);
        arguments.put(ARGUMENT_METADATA_PREFIX, metadataPrefix);
        arguments.put(ARGUMENT_FROM, from);
        arguments.put(ARGUMENT_UNTIL, until);
        arguments.put(ARGUMENT_SET, set);
        return request(arguments);
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(
                ARGUMENT_VERB, Verb.LIST_RECORDS,
                ARGUMENT_RESUMPTION_TOKEN, resumptionToken
        ));
    }

    @Override
    public OaiPmhResponse<GetRecord> getRecord(final URI identifier, final String metadataPrefix)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        return request(Map.of(
                ARGUMENT_VERB, Verb.GET_RECORD,
                ARGUMENT_IDENTIFIER, identifier,
                ARGUMENT_METADATA_PREFIX, metadataPrefix
        ));
    }

    @Override
    public void close() {
        client.close();
    }

    private <T extends OaiPmhBody> OaiPmhResponse<T> request(final Map<String, ?> arguments)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException {
        WebTarget target = this.target;
        for (final Map.Entry<String, ?> argument : arguments.entrySet()) {
            if (argument.getValue() != null) {
                target = target.queryParam(argument.getKey(), argument.getValue());
            }
        }
        try {
            final Response httpResponse = target.request()
                    .accept(MediaType.TEXT_XML_TYPE.withCharset(StandardCharsets.UTF_8.name().toLowerCase(Locale.ROOT)))
                    .header(HttpHeaders.USER_AGENT, "Nefeli 1.0.0-SNAPSHOT") // TODO
                    .header(HEADER_FROM, "thanos.papapetrou@gmail.com") // TODO
                    .get();
            if ((httpResponse.getStatus() == Response.Status.TOO_MANY_REQUESTS.getStatusCode())
                    || (httpResponse.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode())) {
                final String retryAfter = httpResponse.getHeaderString(HttpHeaders.RETRY_AFTER);
                if (retryAfter == null) {
                    throw new WebApplicationException(httpResponse.getStatus());
                } else {
                    throw new RetryAfterException(Integer.parseInt(retryAfter));
                }
            } else if (httpResponse.getStatusInfo().getFamily() == Response.Status.Family.REDIRECTION) {
                try (final OaiPmhClient client = new OaiPmhClient(httpResponse.getLocation().toURL())) { // TODO relativize based on this
                    return client.request(Map.of());
                } catch (final MalformedURLException | URISyntaxException e) {
                    throw new HttpRetryException(ERROR_REDIRECTING, httpResponse.getStatus(),
                            httpResponse.getLocation().toString()); // TODO this should not be retry exception
                }
            } else if (httpResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new WebApplicationException(httpResponse.getStatus());
            }
            final OaiPmhResponse<T> oaiPmhResponse = httpResponse.readEntity(new GenericType<>() {
            });
            if (!oaiPmhResponse.getErrors().isEmpty()) {
                throw new OaiPmhException(oaiPmhResponse.getErrors());
            }
            return oaiPmhResponse;
        } catch (final ProcessingException e) {
            throw new IOException(ERROR_SENDING_REQUEST, e); // TODO this is not necessarily sending error
        }
    }
}
