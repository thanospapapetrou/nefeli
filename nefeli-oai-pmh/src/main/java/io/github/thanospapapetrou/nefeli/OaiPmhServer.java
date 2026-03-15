package io.github.thanospapapetrou.nefeli;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import org.openarchives.oai._2.GetRecord;
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
import org.openarchives.oai._2.Verb;

@Path("/")
public class OaiPmhServer implements OaiPmh {
    private final Clock clock;

    @Context
    private UriInfo info;

    public OaiPmhServer() {
        this(Clock.systemUTC()); // TODO inject and remove this
    }

    public OaiPmhServer(final Clock clock) {
        this.clock = clock;
    }

//    @GET
//    @Path("/oai-pmh")
//    @POST
//    @Produces(MediaType.APPLICATION_XML)
//    public <T extends OaiPmhBody> OaiPmhResponse<T> respond(
////            @QueryParam(ARGUMENT_VERB) final Verb verb,
////            @QueryParam(ARGUMENT_IDENTIFIER) final URI identifier,
////            @QueryParam(ARGUMENT_METADATA_PREFIX) final String metadataPrefix,
////            @QueryParam(ARGUMENT_FROM) final Instant from,
////            @QueryParam(ARGUMENT_UNTIL) final Instant until,
////            @QueryParam(ARGUMENT_SET) final String set,
////            @QueryParam(ARGUMENT_RESUMPTION_TOKEN) final String resumptionToken
//    ) throws MalformedURLException {
//        final Instant time = clock.instant();
////        final Request request =
////                new Request(info.getBaseUri().toURL(), verb, identifier, metadataPrefix, from, until, set,
////                        resumptionToken);
////        try {
////            return new OaiPmhResponse<>(time, request, (Identify) (switch (verb) {
////                case IDENTIFY -> identify();
////                case LIST_METADATA_FORMATS -> listMetadataFormats(identifier);
////                case LIST_SETS -> (resumptionToken == null) ? listSets() : listSets(resumptionToken);
////                case LIST_IDENTIFIERS -> (resumptionToken == null) ? listIdentifiers(metadataPrefix, from, until, set)
////                        : listIdentifiers(resumptionToken);
////                case LIST_RECORDS -> (resumptionToken == null) ? listRecords(metadataPrefix, from, until, set)
////                        : listRecords(resumptionToken);
////                case GET_RECORD -> getRecord(metadataPrefix, identifier);
////            }).getBody());
////        } catch (final OaiPmhException e) {
////            return new OaiPmhResponse<>(time, request, e.getErrors());
////        }
//        return null;
//    }

    @Override
    public OaiPmhResponse<Identify> identify() throws OaiPmhException {
        // TODO
        //        return new OaiPmhResponse<>(null, null, new Identify("foo",
        //                info.getBaseUri().toURL(), adminEmails, ));
        return null;
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
            final Instant until, final String set) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws OaiPmhException {
        return null;
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final String set) throws OaiPmhException {
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
