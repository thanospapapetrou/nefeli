package io.github.thanospapapetrou.nefeli.web;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhResponse;

import io.github.thanospapapetrou.nefeli.OaiPmhClient;

@Path("/test")
public class Test {
    @GET
    @Path("/identify")
    @Produces(MediaType.TEXT_PLAIN)
    public String identify() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create("https://account.rips-irsp.com/index.php/up-j-irsp/oai").toURL())) {
            final OaiPmhResponse<Identify> response = client.identify();
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
                    + "Repository Name: " + response.getBody().getRepositoryName() + "\n"
                    + "Base URL: " + response.getBody().getBaseURL() + "\n"
                    + "Protocol Version: " + response.getBody().getProtocolVersion() + "\n"
                    + "Admin Emails: " + response.getBody().getAdminEmails() + "\n"
                    + "Earliest Datestamp: " + response.getBody().getEarliestDatestamp() + "\n"
                    + "DeletedRecord: " + response.getBody().getDeletedRecord() + "\n"
                    + "Granularity: " + response.getBody().getGranularity() + "\n"
                    + "Compressions: " + response.getBody().getCompressions() + "\n"
                    + "Descriptions: " + response.getBody().getDescriptions();
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    @GET
    @Path("/listMetadataFormats")
    @Produces(MediaType.TEXT_PLAIN)
    public String listMetadataFormats() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create("https://account.rips-irsp.com/index.php/up-j-irsp/oai").toURL())) {
            final OaiPmhResponse<ListMetadataFormats> response = client.listMetadataFormats();
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
                    + "Metadata Formats: " + response.getBody().getMetadataFormats().stream()
                    .map(format ->
                            "\tPrefix: " + format.getMetadataPrefix() + "\n"
                            + "\tSchema: " + format.getSchema() + "\n"
                            + "\tNamespace: " + format.getMetadataNamespace() + "\n")
                    .collect(Collectors.joining("\n"));
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    @GET
    @Path("/listSets")
    @Produces(MediaType.TEXT_PLAIN)
    public String listSets() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create("https://account.rips-irsp.com/index.php/up-j-irsp/oai").toURL())) {
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
                    + "Errors: " + response.getErrors() + "\n"
                    + "\n"
                    + "Sets: " + response.getBody().getSets().stream()
                    .map(set ->
                            "\tSpec: " + set.getSetSpec() + "\n"
                                    + "\tName: " + set.getSetName() + "\n"
                                    + "\tDescriptions: " + set.getSetDescriptions() + "\n")
                    .collect(Collectors.joining("\n"));
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
