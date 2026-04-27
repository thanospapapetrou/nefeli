package io.github.thanospapapetrou.nefeli.web.it

import io.github.thanospapapetrou.nefeli.oai.pmh.AbstractOaiPmhServer
import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhReader
import jakarta.ws.rs.Path
import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.xml.bind.JAXBContext
import org.openarchives.oai._2.OaiPmhBody
import org.openarchives.oai._2.OaiPmhResponse
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit

abstract class OaiPmhBase extends Specification {
    private static final URL URL = URI.create('http://localhost:8080').toURL()

    protected WebTarget target
    private Client client

    def setup() {
        client = ClientBuilder.newClient()
        client.register(new OaiPmhReader(JAXBContext.newInstance(OaiPmhResponse.class).createUnmarshaller()))
        target = client.target(URL.toURI()).path(AbstractOaiPmhServer.getAnnotation(Path).value())
    }

    def cleanup() {
        client.close()
    }

    def verify(final Response response) {
        assert response
        assert response.status == Response.Status.OK.statusCode
        assert response.getHeaderString(HttpHeaders.CONTENT_TYPE)
        assert MediaType.valueOf(response.getHeaderString(HttpHeaders.CONTENT_TYPE)) == MediaType.TEXT_XML_TYPE.withCharset(StandardCharsets.UTF_8.name().toLowerCase(Locale.ROOT))
        true
    }

    def verify(final OaiPmhResponse<OaiPmhBody> oaiPmh) {
        assert oaiPmh
        assert oaiPmh.responseDate
        assert oaiPmh.responseDate.isAfter(Instant.EPOCH)
        assert oaiPmh.request
        assert oaiPmh.request.value == target.uri.toURL()
        true
    }

}
