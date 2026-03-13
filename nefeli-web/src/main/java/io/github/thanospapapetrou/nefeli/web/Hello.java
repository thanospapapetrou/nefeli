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
                    + "Verb:  " + response.getRequest().getVerb() + "\n"
                    + "Errors: " + response.getErrors() + "\n"
                    + "Repository Name:" + response.getBody().getRepositoryName() + "\n"
                    + "Protocol Version:" + response.getBody().getProtocolVersion() + "\n"
                    + "Admin Emails:" + response.getBody().getAdminEmails() + "\n"
                    + "Compressions:" + response.getBody().getCompressions() + "\n"
                    + "Base URL: " + response.getBody().getBaseURL();
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
