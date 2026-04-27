package io.github.thanospapapetrou.nefeli.web.it

import jakarta.ws.rs.client.Client
import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.core.Response
import spock.lang.Specification

class FooIT extends Specification {
    private Client client

    def setup() {
        client = ClientBuilder.newClient()
    }

    def cleanup() {
        client.close()
    }

    def 'Foo'() {
        when:
        final Response response = client.target('http://localhost:8080/oai-pmh?verb=Identify').request().get()
        then:
        response.getStatus() == Response.Status.OK.getStatusCode()
    }
}
