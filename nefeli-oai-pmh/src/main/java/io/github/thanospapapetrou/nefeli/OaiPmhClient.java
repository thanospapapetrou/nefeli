package io.github.thanospapapetrou.nefeli;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.Verb;

public class OaiPmhClient implements AutoCloseable {
    private static final String ARGUMENT_METADATA_PREFIX = "metadataPrefix";
    private static final String ARGUMENT_VERB = "verb";

    private final Client client;
    private final WebTarget target;

    public OaiPmhClient(final URL url) throws URISyntaxException {
        this(ClientBuilder.newClient().register(OaiPmhReader.class), url);
    }

    private OaiPmhClient(final Client client, final URL url) throws URISyntaxException {
        this(client, client.target(url.toURI()));
    }

    private OaiPmhClient(final Client client, final WebTarget target) {
        this.client = client;
        this.target = target;
    }

    public OaiPmhResponse<Identify> identify() {
        return request(Verb.IDENTIFY, Map.of());
    }

    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats() {
        return request(Verb.LIST_METADATA_FORMATS, Map.of());
    }

    public OaiPmhResponse<ListSets> listSets() {
        return request(Verb.LIST_SETS, Map.of());
    }

    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix) {
        return request(Verb.LIST_IDENTIFIERS, Map.of(ARGUMENT_METADATA_PREFIX, metadataPrefix));
    }

    @Override
    public void close() {
        client.close();
    }

    private <T extends OaiPmhBody> OaiPmhResponse<T> request(final Verb verb, final Map<String, String> arguments) {
        WebTarget target = this.target.queryParam(ARGUMENT_VERB, verb);
        for (final Map.Entry<String, String> argument : arguments.entrySet()) {
            target = target.queryParam(argument.getKey(), argument.getValue());
        }
        return target.request().get().readEntity(OaiPmhResponse.class);
    }
}
