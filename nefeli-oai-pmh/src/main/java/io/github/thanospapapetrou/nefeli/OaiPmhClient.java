package io.github.thanospapapetrou.nefeli;

import java.net.URISyntaxException;
import java.net.URL;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.Verb;

public class OaiPmhClient implements AutoCloseable {
    private static final String PARAM_VERB = "verb";

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
        return request(Verb.IDENTIFY);
    }

    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats() {
        return request(Verb.LIST_METADATA_FORMATS);
    }

    public OaiPmhResponse<ListSets> listSets() {
        return request(Verb.LIST_SETS);
    }

    @Override
    public void close() {
        client.close();
    }

    private <T extends OaiPmhBody> OaiPmhResponse<T> request(final Verb verb) {
        final Response response = target.queryParam(PARAM_VERB, verb).request().get();
        return response.readEntity(OaiPmhResponse.class);
    }
}
