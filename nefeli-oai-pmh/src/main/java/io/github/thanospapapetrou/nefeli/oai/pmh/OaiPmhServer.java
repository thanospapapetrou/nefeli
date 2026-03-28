package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.mail.internet.InternetAddress;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import org.openarchives.oai._2.DeletedRecord;
import org.openarchives.oai._2.GetRecord;
import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListRecords;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhError;
import org.openarchives.oai._2.OaiPmhErrorCode;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.Request;
import org.openarchives.oai._2.SetSpec;
import org.openarchives.oai._2.Verb;

import io.github.thanospapapetrou.nefeli.common.Configuration;

@Path("/oai-pmh")
public class OaiPmhServer implements OaiPmh {
    private final String repositoryName;
    private final List<InternetAddress> adminEmails;
    private final DeletedRecord deletedRecord;
    private final Granularity granularity;
    private final List<String> compressions;
    private final Clock clock;

    @Context
    private UriInfo info;

    @Inject
    public OaiPmhServer(@Configuration.Property("nefeli.oai-pmh.server.repositoryName") final String repositoryName,
            @Configuration.Property("nefeli.oai-pmh.server.adminEmails") final List<InternetAddress> adminEmails,
            @Configuration.Property("nefeli.oai-pmh.server.deletedRecord") final DeletedRecord deletedRecord,
            @Configuration.Property("nefeli.oai-pmh.server.granularity") final Granularity granularity,
            @Configuration.Property("nefeli.oai-pmh.server.compressions") final List<String> compressions,
            final Clock clock) {
        this.repositoryName = repositoryName;
        this.adminEmails = adminEmails;
        this.deletedRecord = deletedRecord;
        this.granularity = granularity;
        this.compressions = compressions;
        this.clock = clock;
    }

    @GET
    @Path("/")
    @Produces("text/xml; charset=UTF-8")
    public OaiPmhResponse<? extends OaiPmhBody> respond(
            @QueryParam(ARGUMENT_VERB) final Verb verb,
            @QueryParam(ARGUMENT_IDENTIFIER) final URI identifier,
            @QueryParam(ARGUMENT_METADATA_PREFIX) final String metadataPrefix,
            @QueryParam(ARGUMENT_FROM) final Instant from,
            @QueryParam(ARGUMENT_UNTIL) final Instant until,
            @QueryParam(ARGUMENT_SET) final SetSpec set,
            @QueryParam(ARGUMENT_RESUMPTION_TOKEN) final String resumptionToken
    ) throws MalformedURLException {
        try {
            return switch (verb) {
                case IDENTIFY -> identify();
                case LIST_METADATA_FORMATS -> listMetadataFormats(identifier);
                case LIST_SETS -> (resumptionToken == null) ? listSets() : listSets(resumptionToken);
                case LIST_IDENTIFIERS -> (resumptionToken == null) ? listIdentifiers(metadataPrefix, from, until, set)
                        : listIdentifiers(resumptionToken);
                case LIST_RECORDS -> (resumptionToken == null) ? listRecords(metadataPrefix, from, until, set)
                        : listRecords(resumptionToken);
                case GET_RECORD -> getRecord(metadataPrefix, identifier);
            };
        } catch (final OaiPmhException e) {
            return new OaiPmhResponse<>(clock.instant(),
                    new Request(info.getBaseUri().toURL(), verb, identifier, metadataPrefix, from, until, set,
                            resumptionToken), e.getErrors());
        }
    }

    @Override
    public OaiPmhResponse<Identify> identify() throws OaiPmhException {
        try {
            return new OaiPmhResponse<>(clock.instant(), new Request(info.getBaseUri().toURL(), Verb.IDENTIFY, null,
                    null, null, null, null, null),
                    new Identify(repositoryName, info.getBaseUri().toURL(), adminEmails, Instant.EPOCH, deletedRecord,
                            granularity, compressions, List.of())); // TODO earliest and description
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListSets> listSets() throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListSets> listSets(final String resumptionToken) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String resumptionToken) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<GetRecord> getRecord(final String metadataPrefix, final URI identifier)
            throws OaiPmhException {
        return null;
    }

    private void error(final OaiPmhErrorCode code, final String message) throws OaiPmhException {
        throw new OaiPmhException(List.of(new OaiPmhError(message, code)));
    }
}
