package io.github.thanospapapetrou.nefeli.web;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.openarchives.oai._2.OAIPMHtype;

import io.github.thanospapapetrou.nefeli.OaiPmhClient;

@Path("/world")
public class Hello {
    @GET
    @Path("/world")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        try (final OaiPmhClient client = new OaiPmhClient(
                URI.create("https://account.rips-irsp.com/index.php/up-j-irsp/oai").toURL())) {
            final OAIPMHtype identify = client.identify();
            return identify.getIdentify().getBaseURL() + " " + identify;
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
