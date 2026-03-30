package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

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
import org.openarchives.oai._2.MetadataFormat;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhError;
import org.openarchives.oai._2.OaiPmhErrorCode;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.Record;
import org.openarchives.oai._2.Request;
import org.openarchives.oai._2.SetSpec;
import org.openarchives.oai._2.Verb;

import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhParameterConverterProvider;

@Path("/oai-pmh")
public abstract class AbstractOaiPmhServer implements OaiPmh {
    private static final String ERROR_NO_SETS = "Repository does not support sets";

    private final Clock clock;
    private final OaiPmhParameterConverterProvider provider;
    private final String repositoryName;
    private final List<InternetAddress> adminEmails;
    private final DeletedRecord deletedRecord;
    private final Granularity granularity;
    private final List<String> compressions;

    @Context
    private UriInfo info;

    protected AbstractOaiPmhServer(final Clock clock, final OaiPmhParameterConverterProvider provider,
            final String repositoryName, final List<InternetAddress> adminEmails, final DeletedRecord deletedRecord,
            final Granularity granularity, final List<String> compressions) {
        this.clock = clock;
        this.provider = provider;
        this.repositoryName = repositoryName;
        this.adminEmails = adminEmails;
        this.deletedRecord = deletedRecord;
        this.granularity = granularity;
        this.compressions = compressions;
    }

    @GET
    @Path("/")
    @Produces("text/xml; charset=UTF-8")
    public OaiPmhResponse<? extends OaiPmhBody> respond(@QueryParam(ARGUMENT_VERB) final Verb verb,
            @QueryParam(ARGUMENT_IDENTIFIER) final URI identifier,
            @QueryParam(ARGUMENT_METADATA_PREFIX) final String metadataPrefix,
            @QueryParam(ARGUMENT_FROM) final Instant from, @QueryParam(ARGUMENT_UNTIL) final Instant until,
            @QueryParam(ARGUMENT_SET) final SetSpec set,
            @QueryParam(ARGUMENT_RESUMPTION_TOKEN) final String resumptionToken) throws MalformedURLException {
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
    public OaiPmhResponse<Identify> identify() throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request,
                new Identify(repositoryName, info.getAbsolutePath().toURL(), adminEmails, getEarliestDatestamp(),
                        deletedRecord, granularity, compressions, List.of())); // TODO description
    }

    @Override
    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier)
            throws OaiPmhException, MalformedURLException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, new ListMetadataFormats(listMetadataFormats(datestamp)));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets() throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listSets(datestamp, null));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets(final String resumptionToken)
            throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listSets(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listIdentifiers(datestamp, metadataPrefix, from, until, set));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws MalformedURLException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listIdentifiers(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listRecords(datestamp, metadataPrefix, from, until, set));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String resumptionToken)
            throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request, listRecords(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<GetRecord> getRecord(final String metadataPrefix, final URI identifier)
            throws MalformedURLException, OaiPmhException {
        final Request request = getRequest();
        // TODO validate
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, request,
                new GetRecord(getRecord(datestamp, metadataPrefix, identifier)));
    }

    protected abstract Instant getEarliestDatestamp();

    protected abstract List<MetadataFormat> listMetadataFormats(final Instant datestamp);

    protected ListSets listSets(final Instant datestamp, final String resumptionToken) throws OaiPmhException {
        return error(OaiPmhErrorCode.NO_SET_HIERARCHY, ERROR_NO_SETS);
    }

    protected abstract ListIdentifiers listIdentifiers(final Instant datestamp, final String metadataPrefix,
            final Instant from, final Instant until, final SetSpec set);

    protected abstract ListIdentifiers listIdentifiers(final Instant datestamp, final String resumptionToken);

    protected abstract ListRecords listRecords(final Instant datestamp, final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set);

    protected abstract ListRecords listRecords(final Instant datestamp, final String resumptionToken);

    protected abstract Record getRecord(final Instant datestamp, final String metadataPrefix, final URI identifier);

    private Request getRequest() throws MalformedURLException {
        return new Request(info.getAbsolutePath().toURL(), getArgument(ARGUMENT_VERB, Verb.class),
                getArgument(ARGUMENT_IDENTIFIER, URI.class), getArgument(ARGUMENT_METADATA_PREFIX, String.class),
                getArgument(ARGUMENT_FROM, Instant.class), getArgument(ARGUMENT_UNTIL, Instant.class),
                getArgument(ARGUMENT_SET, SetSpec.class), getArgument(ARGUMENT_RESUMPTION_TOKEN, String.class));
    }

    private <T> T getArgument(final String argument, final Class<T> clazz) {
        final List<String> arguments = info.getQueryParameters().get(argument);
        return ((arguments == null) || arguments.isEmpty()) ? null : ((clazz == String.class) ? (T) arguments.getFirst()
                : provider.getConverter(clazz, null, null).fromString(arguments.getFirst()));
    }

    private <T> T error(final OaiPmhErrorCode code, final String message) throws OaiPmhException {
        throw new OaiPmhException(List.of(new OaiPmhError(message, code)));
    }
}
