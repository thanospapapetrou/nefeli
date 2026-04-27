package io.github.thanospapapetrou.nefeli.web.it

import io.github.thanospapapetrou.nefeli.oai.pmh.AbstractOaiPmhServer
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmh
import jakarta.ws.rs.core.GenericType
import jakarta.ws.rs.core.Response
import org.openarchives.oai._2.OaiPmhBody
import org.openarchives.oai._2.OaiPmhErrorCode
import org.openarchives.oai._2.OaiPmhResponse
import org.openarchives.oai._2.Verb

import java.time.Instant

class ErrorIT extends OaiPmhBase {
    def 'Test missing verb'() {
        when:
        final Response response = target.request().get()
        then:
        verify(response)
        when:
        final OaiPmhResponse<OaiPmhBody> oaiPmh = response.readEntity(new GenericType<OaiPmhResponse<OaiPmhBody>>() {})
        then:
        verify(oaiPmh)
        !oaiPmh.request.verb
        !oaiPmh.request.identifier
        !oaiPmh.request.metadataPrefix
        !oaiPmh.request.from
        !oaiPmh.request.until
        !oaiPmh.request.set
        !oaiPmh.request.resumptionToken
        oaiPmh.errors
        oaiPmh.errors*.value == [AbstractOaiPmhServer.ERROR_VERB_MISSING]
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_VERB]
        !oaiPmh.body
    }

    def 'Test illegal verb'() {
        when:
        final Response response = target.queryParam(OaiPmh.ARGUMENT_VERB, 'foo').request().get()
        then:
        verify(response)
        when:
        final OaiPmhResponse<OaiPmhBody> oaiPmh = response.readEntity(new GenericType<OaiPmhResponse<OaiPmhBody>>() {})
        then:
        verify(oaiPmh)
        !oaiPmh.request.verb
        !oaiPmh.request.identifier
        !oaiPmh.request.metadataPrefix
        !oaiPmh.request.from
        !oaiPmh.request.until
        !oaiPmh.request.set
        !oaiPmh.request.resumptionToken
        oaiPmh.errors
        oaiPmh.errors*.value == [AbstractOaiPmhServer.ERROR_VERB_ILLEGAL]
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_VERB]
        !oaiPmh.body
    }

    def 'Test repeated verb'() {
        when:
        final Response response = target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.IDENTIFY, Verb.IDENTIFY).request().get()
        then:
        verify(response)
        when:
        final OaiPmhResponse<OaiPmhBody> oaiPmh = response.readEntity(new GenericType<OaiPmhResponse<OaiPmhBody>>() {})
        then:
        verify(oaiPmh)
        !oaiPmh.request.verb
        !oaiPmh.request.identifier
        !oaiPmh.request.metadataPrefix
        !oaiPmh.request.from
        !oaiPmh.request.until
        !oaiPmh.request.set
        !oaiPmh.request.resumptionToken
        oaiPmh.errors
        oaiPmh.errors*.value == [AbstractOaiPmhServer.ERROR_VERB_REPEATED]
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_VERB]
        !oaiPmh.body
    }
}
