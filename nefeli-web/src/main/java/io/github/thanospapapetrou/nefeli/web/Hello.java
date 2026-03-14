package io.github.thanospapapetrou.nefeli.web;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.OaiPmhResponse;

import io.github.thanospapapetrou.nefeli.OaiPmhClient;

@Path("/world")
public class Hello {
    @GET
    @Path("/world")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
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
}
