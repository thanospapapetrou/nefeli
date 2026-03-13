package io.github.thanospapapetrou.nefeli;

import java.net.URISyntaxException;
import java.net.URL;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.VerbType;

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

    public OAIPMHtype identify() {
        final Response response = target.queryParam(PARAM_VERB, VerbType.IDENTIFY.value()).request().get();
        return response.readEntity(OAIPMHtype.class);
    }

    @Override
    public void close() {
        client.close();
    }
}
