package io.github.thanospapapetrou.nefeli.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.openarchives.oai._2.Description;
import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhError;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.Request;

import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhClient;
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhException;
import io.github.thanospapapetrou.nefeli.db.DaoException;
import io.github.thanospapapetrou.nefeli.db.RepositoryDao;

@Path("/test")
public class Test {
    private static final String BASE_URL = "https://www.ijrah.com/index.php/ijrah/oai";

    @GET
    @Path("/data")
    @Produces(MediaType.TEXT_PLAIN)
    public String data() {
        final RepositoryDao dao = CDI.current().select(RepositoryDao.class).get();
        try {
            return dao.getRepositories().toString();
        } catch (final DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/identify")
    @Produces(MediaType.TEXT_PLAIN)
    public String identify() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create(BASE_URL).toURL())) {
            final OaiPmhResponse<Identify> response = client.identify();
            return formatResponse(response, this::formatIdentify);
        } catch (final IOException | URISyntaxException | OaiPmhException e) {
            return null;
        }
    }

    @GET
    @Path("/listMetadataFormats")
    @Produces(MediaType.TEXT_PLAIN)
    public String listMetadataFormats() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create(BASE_URL).toURL())) {
            final OaiPmhResponse<ListMetadataFormats> response = client.listMetadataFormats(null);
            return "Response Date: " + response.getResponseDate() + "\n"
                    + "Request: " + response.getRequest().getValue() + "\n"
                    + "\tVerb: " + response.getRequest().getVerb() + "\n"
                    + "\tIdentifier: " + response.getRequest().getIdentifier() + "\n"
                    + "\tMetadata Prefix: " + response.getRequest().getMetadataPrefix() + "\n"
                    + "\tFrom: " + response.getRequest().getFrom() + "\n"
                    + "\tUntil: " + response.getRequest().getUntil() + "\n"
                    + "\tSet: " + response.getRequest().getSet() + "\n"
                    + "\tResumption Token: " + response.getRequest().getResumptionToken() + "\n"
                    + "Errors: " + response.getErrors() + "\n"
                    + "\n"
                    + "Metadata Formats:\n" + response.getBody().getMetadataFormats().stream()
                    .map(format ->
                            "\tPrefix: " + format.getMetadataPrefix() + "\n"
                            + "\tSchema: " + format.getSchema() + "\n"
                            + "\tNamespace: " + format.getMetadataNamespace() + "\n")
                    .collect(Collectors.joining());
        } catch (final IOException | URISyntaxException | OaiPmhException e) {
            return null;
        }
    }

    @GET
    @Path("/listSets")
    @Produces(MediaType.TEXT_PLAIN)
    public String listSets() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create(BASE_URL).toURL())) {
            final OaiPmhResponse<ListSets> response = client.listSets();
            return "Response Date: " + response.getResponseDate() + "\n"
                    + "Request: " + response.getRequest().getValue() + "\n"
                    + "\tVerb: " + response.getRequest().getVerb() + "\n"
                    + "\tIdentifier: " + response.getRequest().getIdentifier() + "\n"
                    + "\tMetadata Prefix: " + response.getRequest().getMetadataPrefix() + "\n"
                    + "\tFrom: " + response.getRequest().getFrom() + "\n"
                    + "\tUntil: " + response.getRequest().getUntil() + "\n"
                    + "\tSet: " + response.getRequest().getSet() + "\n"
                    + "\tResumption Token: " + response.getRequest().getResumptionToken() + "\n"
                    + "Errors: " + response.getErrors().stream().map(this::formatError).collect(Collectors.joining()) + "\n"
                    + "Sets:\n" + response.getBody().getSets().stream()
                    .map(set ->
                            "\tSpec: " + set.getSetSpec() + "\n"
                                    + "\tName: " + set.getSetName() + "\n"
                                    + "\tDescriptions: " + set.getSetDescriptions() + "\n")
                    .collect(Collectors.joining()) + "\n"
                    + ((response.getBody().getResumptionToken() == null) ? "" :
                    ("Resumption Token: " + response.getBody().getResumptionToken().getValue() + "\n"
                            + "\tExpiration Date: " + response.getBody().getResumptionToken().getExpirationDate() + "\n"
                            + "\tComplete List Size: " + response.getBody().getResumptionToken().getCompleteListSize()
                            + "\n"
                            + "\tCursor: " + response.getBody().getResumptionToken().getCursor() + "\n"));
        } catch (final IOException | URISyntaxException | OaiPmhException e) {
            return null;
        }
    }

    @GET
    @Path("/listIdentifiers")
    @Produces(MediaType.TEXT_PLAIN)
    public String listIdentifiers() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create(BASE_URL).toURL())) {
            final OaiPmhResponse<ListIdentifiers> response = client.listIdentifiers("oai_dc");
            return "Response Date: " + response.getResponseDate() + "\n"
                    + "Request: " + response.getRequest().getValue() + "\n"
                    + "\tVerb: " + response.getRequest().getVerb() + "\n"
                    + "\tIdentifier: " + response.getRequest().getIdentifier() + "\n"
                    + "\tMetadata Prefix: " + response.getRequest().getMetadataPrefix() + "\n"
                    + "\tFrom: " + response.getRequest().getFrom() + "\n"
                    + "\tUntil: " + response.getRequest().getUntil() + "\n"
                    + "\tSet: " + response.getRequest().getSet() + "\n"
                    + "\tResumption Token: " + response.getRequest().getResumptionToken() + "\n"
                    + "Errors: " + response.getErrors().stream().map(this::formatError).collect(Collectors.joining())
                    + "\n"
                    + "\n"
                    + ((response.getBody() == null) ? "" : "Headers:\n" + response.getBody().getHeaders().stream()
                    .map(header ->
                            "\tIdentifier: " + header.getIdentifier() + "\n"
                                    + "\tDatestamp: " + header.getDatestamp() + "\n"
                                    + "\tSet Spects: " + header.getSetSpecs() + "\n"
                                    + "\tDeleted: " + header.isDeleted())
                    .collect(Collectors.joining()) + "\n"
                    + ((response.getBody().getResumptionToken() == null) ? "" :
                    ("Resumption Token: " + response.getBody().getResumptionToken().getValue() + "\n"
                            + "\tExpiration Date: " + response.getBody().getResumptionToken().getExpirationDate() + "\n"
                            + "\tComplete List Size: " + response.getBody().getResumptionToken().getCompleteListSize()
                            + "\n"
                            + "\tCursor: " + response.getBody().getResumptionToken().getCursor() + "\n")));
        } catch (final IOException | URISyntaxException | OaiPmhException e) {
            return null;
        }
    }

    private <T extends OaiPmhBody> String formatResponse(final OaiPmhResponse<T> response,
            Function<T, String> formatBody) {
        return String.format("Response Date: %1$s\n%2$s\nErrors: %3$s\n%4$s",
                response.getResponseDate(), formatRequest(response.getRequest()),
                response.getErrors().stream().map(this::formatError).collect(Collectors.joining()),
                formatBody.apply(response.getBody()));
    }

    private String formatRequest(final Request request) {
        return (request == null) ? "" : String.format("""
                        Request: %1$s
                        \tVerb: %2$s
                        \tIdentifier: %3$s
                        \tMetadata Prefix: %4$s
                        \tFrom: %5$s
                        \tUntil: %6$s
                        \tSet: %7$s
                        \tResumption Token: %8$s
                        """,
                request.getValue(), request.getVerb(), request.getIdentifier(), request.getMetadataPrefix(),
                request.getFrom(), request.getUntil(), request.getSet(), request.getResumptionToken());
    }

    private String formatError(final OaiPmhError error) {
        return String.format("\tValue: %1$s\n\tCode: %2$s\n", error.getValue(), error.getCode());
    }

    private String formatIdentify(final Identify identify) {
        return String.format("""
                        \tRepository Name: %1$s
                        \tBase URL: %2$s
                        \tProtocol Version: %3$s
                        \tAdmin Emails: %4$s
                        \tEarliest Datestamp: %5$s
                        \tDeleted Record: %6$s
                        \tGranularity: %7$s
                        \tCompressions: %8$s
                        \tDescriptions: %9$s
                        """,
                identify.getRepositoryName(), identify.getBaseURL(), identify.getProtocolVersion(),
                identify.getAdminEmails(), identify.getEarliestDatestamp(), identify.getDeletedRecord(),
                identify.getGranularity(), identify.getCompressions(), identify.getDescriptions().stream()
                        .map(Description::getDescription)
                        .map(element -> element.getNamespaceURI() + " " + element.getTagName())
                        .toList());
    }
}
