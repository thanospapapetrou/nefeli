package io.github.thanospapapetrou.nefeli.web.it

import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmh
import jakarta.ws.rs.core.GenericType
import jakarta.ws.rs.core.Response
import org.openarchives.oai._2.*

import java.time.Instant

class IdentifyIT extends OaiPmhBase {
    def 'Test Identify'() {
        when:
        final Response response = target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.IDENTIFY).request().get()
        then:
        verify(response)
        when:
        final OaiPmhResponse<Identify> identify = response.readEntity(new GenericType<OaiPmhResponse<Identify>>() {})
        then:
        verify(identify)
        identify.request.verb == Verb.IDENTIFY
        !identify.request.identifier
        !identify.request.metadataPrefix
        !identify.request.from
        !identify.request.until
        !identify.request.set
        !identify.request.resumptionToken
        identify.errors == []
        identify.body
        identify.body.repositoryName == 'Nefeli'
        identify.body.baseUrl == target.uri.toURL()
        identify.body.protocolVersion == Identify.VERSION
        identify.body.adminEmails*.toString() == ['thanos.papapetrou@gmail.com']
        identify.body.earliestDatestamp == Instant.EPOCH // TODO
        identify.body.earliestDatestamp.isBefore(identify.responseDate)
        identify.body.deletedRecord == DeletedRecord.PERSISTENT
        identify.body.granularity == Granularity.SECONDS
        identify.body.compressions == [Identify.COMPRESSION_GZIP]
        identify.body.descriptions == []
    }
}
