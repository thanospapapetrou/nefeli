package io.github.thanospapapetrou.nefeli;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

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

public class OaiPmhClient implements OaiPmh, AutoCloseable {
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

    @Override
    public OaiPmhResponse<Identify> identify() throws OaiPmhException {
        return request(Verb.IDENTIFY, Map.of());
    }

    @Override
    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier) throws OaiPmhException {
        return request(Verb.LIST_METADATA_FORMATS, Collections.singletonMap(ARGUMENT_IDENTIFIER, identifier));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets() throws OaiPmhException {
        return request(Verb.LIST_SETS, Map.of());
    }

    @Override
    public OaiPmhResponse<ListSets> listSets(final String resumptionToken) throws OaiPmhException {
        return request(Verb.LIST_SETS, Map.of(ARGUMENT_RESUMPTION_TOKEN, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) throws OaiPmhException {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put(ARGUMENT_METADATA_PREFIX, metadataPrefix);
        arguments.put(ARGUMENT_FROM, from);
        arguments.put(ARGUMENT_UNTIL, until);
        arguments.put(ARGUMENT_SET, set);
        return request(Verb.LIST_IDENTIFIERS, arguments);
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws OaiPmhException {
        return request(Verb.LIST_IDENTIFIERS, Map.of(ARGUMENT_RESUMPTION_TOKEN, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws OaiPmhException {
        final Map<String, Object> arguments = new HashMap<>();
        arguments.put(ARGUMENT_METADATA_PREFIX, metadataPrefix);
        arguments.put(ARGUMENT_FROM, from);
        arguments.put(ARGUMENT_UNTIL, until);
        arguments.put(ARGUMENT_SET, set);
        return request(Verb.LIST_RECORDS, arguments);
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String resumptionToken) throws OaiPmhException {
        return request(Verb.LIST_RECORDS, Map.of(ARGUMENT_RESUMPTION_TOKEN, resumptionToken));
    }

    @Override
    public OaiPmhResponse<GetRecord> getRecord(final String metadataPrefix, final URI identifier)
            throws OaiPmhException {
        return request(Verb.GET_RECORD, Map.of(ARGUMENT_METADATA_PREFIX, metadataPrefix, ARGUMENT_IDENTIFIER, identifier));
    }

    @Override
    public void close() {
        client.close();
    }

    private <T extends OaiPmhBody> OaiPmhResponse<T> request(final Verb verb, final Map<String, ?> arguments)
            throws OaiPmhException {
        WebTarget target = this.target.queryParam(ARGUMENT_VERB, verb);
        for (final Map.Entry<String, ?> argument : arguments.entrySet()) {
            if (argument.getValue() != null) {
                target = target.queryParam(argument.getKey(), argument.getValue());
            }
        }
        final OaiPmhResponse<T> response = target.request().get().readEntity(OaiPmhResponse.class);
        if (!response.getErrors().isEmpty()) {
            throw new OaiPmhException(response.getErrors());
        }
        return response;
    }
}
