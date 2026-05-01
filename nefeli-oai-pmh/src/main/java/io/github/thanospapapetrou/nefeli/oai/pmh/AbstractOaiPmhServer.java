package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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
    private static final String ERROR_ARGUMENT_ILLEGAL = "OAI-PMH request contains illegal argument %1$s";
    private static final String ERROR_ARGUMENT_EXCLUSIVE =
            "OAI-PMH request contains both mutually exclusive arguments %1$s and %2$s";
    private static final String ERROR_ARGUMENT_INVALID =
            "OAI-PMH request contains argument %1$s with invalid value %2$s";
    private static final String ERROR_ARGUMENT_MISSING = "OAI-PMH request is missing required argument %1$s";
    private static final String ERROR_ARGUMENT_MISSING_EXCLUSIVE =
            "OAI-PMH request is missing exclusively required arguments %1$s or %2$s";
    private static final String ERROR_ARGUMENT_REPEATED = "OAI-PMH request contains repeated argument %1$s";
    private static final String ERROR_NO_METADATA_FORMATS = "There are no metadata formats available";

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
    @Produces(OaiPmhResponse.CONTENT_TYPE)
    public OaiPmhResponse<? extends OaiPmhBody> respond(@QueryParam(ARGUMENT_VERB) final Verb verb,
            @QueryParam(ARGUMENT_IDENTIFIER) final URI identifier,
            @QueryParam(ARGUMENT_METADATA_PREFIX) final String metadataPrefix,
            @QueryParam(ARGUMENT_FROM) final Instant from, @QueryParam(ARGUMENT_UNTIL) final Instant until,
            @QueryParam(ARGUMENT_SET) final SetSpec set,
            @QueryParam(ARGUMENT_RESUMPTION_TOKEN) final String resumptionToken) throws MalformedURLException {
        try {
            return switch (validateVerb(verb)) {
                case IDENTIFY -> {
                    validateIdentify();
                    yield identify();
                }
                case LIST_METADATA_FORMATS -> {
                    validateListMetadataFormats(identifier);
                    yield listMetadataFormats(identifier);
                }
                case LIST_SETS -> {
                    validateListSets(resumptionToken);
                    yield listSets(resumptionToken);
                }
                case LIST_IDENTIFIERS -> {
                    validateListIdentifiersListRecords(metadataPrefix, from, until, set, resumptionToken);
                    yield (resumptionToken == null) ? listIdentifiers(metadataPrefix, from, until, set)
                            : listIdentifiers(resumptionToken);
                }
                case LIST_RECORDS -> {
                    validateListIdentifiersListRecords(metadataPrefix, from, until, set, resumptionToken);
                    yield (resumptionToken == null) ? listRecords(metadataPrefix, from, until, set)
                            : listRecords(resumptionToken);
                }
                case GET_RECORD -> {
                    validateGetRecord(identifier, metadataPrefix);
                    yield getRecord(identifier, metadataPrefix);
                }
            };
        } catch (final OaiPmhException e) {
            final boolean isBadVerbOrBadArgument = e.getErrors().stream()
                    .map(OaiPmhError::getCode)
                    .anyMatch(code -> OaiPmhErrorCode.BAD_VERB.equals(code)
                            || OaiPmhErrorCode.BAD_ARGUMENT.equals(code));
            return new OaiPmhResponse<>(clock.instant(),
                    new Request(info.getAbsolutePath().toURL(),
                            isBadVerbOrBadArgument ? null : verb,
                            isBadVerbOrBadArgument ? null : identifier,
                            isBadVerbOrBadArgument ? null : metadataPrefix,
                            isBadVerbOrBadArgument ? null : from,
                            isBadVerbOrBadArgument ? null : until,
                            isBadVerbOrBadArgument ? null : set,
                            isBadVerbOrBadArgument ? null : resumptionToken),
                    e.getErrors());
        }
    }

    @Override
    public OaiPmhResponse<Identify> identify() throws MalformedURLException, OaiPmhException {
        return new OaiPmhResponse<>(clock.instant(), getRequest(),
                new Identify(repositoryName, info.getAbsolutePath().toURL(), adminEmails, getEarliestDatestamp(),
                        deletedRecord, granularity, compressions, List.of())); // TODO descriptions
    }

    @Override
    public OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier)
            throws OaiPmhException, MalformedURLException {
        final Instant datestamp = clock.instant();
        final List<MetadataFormat> metadataFormats = listMetadataFormats(datestamp, identifier);
        if (metadataFormats.isEmpty()) {
            throw new OaiPmhException(List.of(new OaiPmhError(ERROR_NO_METADATA_FORMATS,
                    OaiPmhErrorCode.NO_METADATA_FORMATS)));
        }
        return new OaiPmhResponse<>(datestamp, getRequest(), new ListMetadataFormats(metadataFormats));
    }

    @Override
    public OaiPmhResponse<ListSets> listSets() throws MalformedURLException, OaiPmhException {
        return listSets(null);
    }

    @Override
    public OaiPmhResponse<ListSets> listSets(final String resumptionToken) throws MalformedURLException,
            OaiPmhException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), listSets(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) throws MalformedURLException, OaiPmhException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), listIdentifiers(datestamp, metadataPrefix, from, until,
                set));
    }

    @Override
    public OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws MalformedURLException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), listIdentifiers(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws MalformedURLException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), listRecords(datestamp, metadataPrefix, from, until, set));
    }

    @Override
    public OaiPmhResponse<ListRecords> listRecords(final String resumptionToken)
            throws MalformedURLException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), listRecords(datestamp, resumptionToken));
    }

    @Override
    public OaiPmhResponse<GetRecord> getRecord(final URI identifier, final String metadataPrefix)
            throws MalformedURLException {
        final Instant datestamp = clock.instant();
        return new OaiPmhResponse<>(datestamp, getRequest(), new GetRecord(getRecord(datestamp, identifier,
                metadataPrefix)));
    }

    protected abstract Instant getEarliestDatestamp();

    protected abstract List<MetadataFormat> listMetadataFormats(final Instant datestamp, final URI identifier)
            throws OaiPmhException;

    protected abstract ListSets listSets(final Instant datestamp, final String resumptionToken) throws OaiPmhException;

    protected abstract ListIdentifiers listIdentifiers(final Instant datestamp, final String metadataPrefix,
            final Instant from, final Instant until, final SetSpec set);

    protected abstract ListIdentifiers listIdentifiers(final Instant datestamp, final String resumptionToken);

    protected abstract ListRecords listRecords(final Instant datestamp, final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set);

    protected abstract ListRecords listRecords(final Instant datestamp, final String resumptionToken);

    protected abstract Record getRecord(final Instant datestamp, final URI identifier, final String metadataPrefix);

    private Verb validateVerb(final Verb verb) throws OaiPmhException {
        if (!info.getQueryParameters().containsKey(ARGUMENT_VERB)) {
            throw new OaiPmhException(List.of(new OaiPmhError(ERROR_ARGUMENT_MISSING.formatted(ARGUMENT_VERB),
                    OaiPmhErrorCode.BAD_VERB)));
        } else if (info.getQueryParameters().get(ARGUMENT_VERB).size() > 1) {
            throw new OaiPmhException(List.of(new OaiPmhError(ERROR_ARGUMENT_REPEATED.formatted(ARGUMENT_VERB),
                    OaiPmhErrorCode.BAD_VERB)));
        } else if (verb == null) {
            throw new OaiPmhException(List.of(new OaiPmhError(ERROR_ARGUMENT_INVALID.formatted(ARGUMENT_VERB,
                    info.getQueryParameters().get(ARGUMENT_VERB).getFirst()), OaiPmhErrorCode.BAD_VERB)));
        }
        return verb;
    }

    private void validateIdentify() throws OaiPmhException {
        validate(checkIllegal(ARGUMENT_METADATA_PREFIX, ARGUMENT_FROM, ARGUMENT_UNTIL, ARGUMENT_SET,
                ARGUMENT_RESUMPTION_TOKEN, ARGUMENT_IDENTIFIER));
    }

    private void validateListMetadataFormats(final URI identifier) throws OaiPmhException {
        validate(Stream.of(checkOptional(ARGUMENT_IDENTIFIER, identifier)),
                checkIllegal(ARGUMENT_METADATA_PREFIX, ARGUMENT_FROM, ARGUMENT_UNTIL, ARGUMENT_SET,
                        ARGUMENT_RESUMPTION_TOKEN));
    }

    private void validateListSets(final String resumptionToken) throws OaiPmhException {
        validate(Stream.of(checkOptional(ARGUMENT_RESUMPTION_TOKEN, resumptionToken)),
                checkIllegal(ARGUMENT_METADATA_PREFIX, ARGUMENT_FROM, ARGUMENT_UNTIL, ARGUMENT_SET,
                        ARGUMENT_IDENTIFIER));
    }

    private void validateListIdentifiersListRecords(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set, final String resumptionToken) throws OaiPmhException {
        validate(Stream.of(checkExclusive(ARGUMENT_METADATA_PREFIX, metadataPrefix, ARGUMENT_RESUMPTION_TOKEN,
                        resumptionToken)),
                ((metadataPrefix != null) && (resumptionToken == null))
                        ? Stream.of(checkOptional(ARGUMENT_FROM, from), checkOptional(ARGUMENT_UNTIL, until),
                        checkOptional(ARGUMENT_SET, set))
                        : Stream.empty(),
                Stream.of(checkIllegal(OaiPmh.ARGUMENT_IDENTIFIER)));
    }

    private void validateGetRecord(final URI identifier, final String metadataPrefix) throws OaiPmhException {
        validate(checkRequired(Map.of(ARGUMENT_IDENTIFIER, identifier, ARGUMENT_METADATA_PREFIX, metadataPrefix)),
                checkIllegal(ARGUMENT_FROM, ARGUMENT_UNTIL, ARGUMENT_SET, ARGUMENT_RESUMPTION_TOKEN));
    }

    private void validate(final Stream<OaiPmhError>... errors) throws OaiPmhException {
        final List<OaiPmhError> list = Arrays.stream(errors).reduce(Stream.empty(), Stream::concat)
                .filter(Objects::nonNull)
                .toList();
        if (!list.isEmpty()) {
            throw new OaiPmhException(list);
        }
    }

    private Stream<OaiPmhError> checkRequired(final Map<String, Object> argumentValues) {
        return argumentValues.entrySet().stream()
                .map(entry -> checkRequired(entry.getKey(), entry.getValue()));
    }

    private OaiPmhError checkRequired(final String argument, final Object value) {
        if (!info.getQueryParameters().containsKey(argument)) {
            return new OaiPmhError(ERROR_ARGUMENT_MISSING.formatted(argument), OaiPmhErrorCode.BAD_ARGUMENT);
        } else if (info.getQueryParameters().get(argument).size() > 1) {
            return new OaiPmhError(ERROR_ARGUMENT_REPEATED.formatted(argument), OaiPmhErrorCode.BAD_ARGUMENT);
        } else if (value == null) {
            return new OaiPmhError(ERROR_ARGUMENT_INVALID.formatted(argument,
                    info.getQueryParameters().get(argument).getFirst()), OaiPmhErrorCode.BAD_ARGUMENT);
        }
        return null;
    }

    private OaiPmhError checkOptional(final String argument, final Object value) {
        if (info.getQueryParameters().containsKey(argument) && (info.getQueryParameters().get(argument).size() > 1)) {
            return new OaiPmhError(ERROR_ARGUMENT_REPEATED.formatted(argument), OaiPmhErrorCode.BAD_ARGUMENT);
        } else if (info.getQueryParameters().containsKey(argument) && (value == null)) {
            return new OaiPmhError(ERROR_ARGUMENT_INVALID.formatted(argument,
                    info.getQueryParameters().get(argument).getFirst()), OaiPmhErrorCode.BAD_ARGUMENT);
        }
        return null;
    }

    private OaiPmhError checkExclusive(final String argument1, final Object value1, final String argument2,
            final Object value2) {
        if (!(info.getQueryParameters().containsKey(argument1) || info.getQueryParameters().containsKey(argument2))) {
            return new OaiPmhError(ERROR_ARGUMENT_MISSING_EXCLUSIVE.formatted(argument1, argument2),
                    OaiPmhErrorCode.BAD_ARGUMENT);
        } else if (info.getQueryParameters().containsKey(argument1)
                && info.getQueryParameters().containsKey(argument2)) {
            return new OaiPmhError(ERROR_ARGUMENT_EXCLUSIVE.formatted(argument1, argument2),
                    OaiPmhErrorCode.BAD_ARGUMENT);
        }
        return info.getQueryParameters().containsKey(argument1) ? checkOptional(argument1, value1)
                : checkOptional(argument2, value2);
    }

    private Stream<OaiPmhError> checkIllegal(final String... arguments) {
        return Arrays.stream(arguments)
                .map(this::checkIllegal);
    }

    private OaiPmhError checkIllegal(final String argument) {
        return info.getQueryParameters().containsKey(argument)
                ? new OaiPmhError(ERROR_ARGUMENT_ILLEGAL.formatted(argument), OaiPmhErrorCode.BAD_ARGUMENT) : null;
    }

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
}
