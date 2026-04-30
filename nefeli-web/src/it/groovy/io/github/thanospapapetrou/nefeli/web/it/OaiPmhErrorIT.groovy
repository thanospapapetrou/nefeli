package io.github.thanospapapetrou.nefeli.web.it

import io.github.thanospapapetrou.nefeli.oai.pmh.AbstractOaiPmhServer
import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmh
import jakarta.ws.rs.client.WebTarget
import jakarta.ws.rs.core.GenericType
import jakarta.ws.rs.core.Response
import org.openarchives.oai._2.OaiPmhBody
import org.openarchives.oai._2.OaiPmhErrorCode
import org.openarchives.oai._2.OaiPmhResponse
import org.openarchives.oai._2.Verb
import spock.lang.Unroll

class OaiPmhErrorIT extends OaiPmhBase {
    @Unroll('Test bad verb (error: #error)')
    def 'Test bad verb'() {
        when:
        final Response response = target.queryParam(OaiPmh.ARGUMENT_VERB, verbs as String[]).request().get()
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
        oaiPmh.errors*.value == [error]
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_VERB]
        !oaiPmh.body
        where:
        verbs          || error
        []             || AbstractOaiPmhServer.ERROR_ARGUMENT_MISSING.formatted(OaiPmh.ARGUMENT_VERB)
        ['foo', 'bar'] || AbstractOaiPmhServer.ERROR_ARGUMENT_REPEATED.formatted(OaiPmh.ARGUMENT_VERB)
        ['foo']        || AbstractOaiPmhServer.ERROR_ARGUMENT_INVALID.formatted(OaiPmh.ARGUMENT_VERB, 'foo')
    }

    def 'Test identify bad argument'() {
        given:
        final Map<String, String> illegal = [
                (OaiPmh.ARGUMENT_METADATA_PREFIX) : 'foo',
                (OaiPmh.ARGUMENT_FROM)            : 'bar',
                (OaiPmh.ARGUMENT_UNTIL)           : 'baz',
                (OaiPmh.ARGUMENT_SET)             : 'qux',
                (OaiPmh.ARGUMENT_RESUMPTION_TOKEN): 'quux',
                (OaiPmh.ARGUMENT_IDENTIFIER)      : 'corge'
        ]
        WebTarget target = this.target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.IDENTIFY)
        illegal.each { target = target.queryParam(it.key, it.value) }
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
        oaiPmh.errors*.value == illegal.keySet().collect(AbstractOaiPmhServer.ERROR_ARGUMENT_ILLEGAL.&formatted)
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_ARGUMENT] * illegal.size()
        !oaiPmh.body
    }

    def 'Test list metadata formats bad argument'() {
        given:
        final Map<String, String> illegal = [
                (OaiPmh.ARGUMENT_METADATA_PREFIX) : 'foo',
                (OaiPmh.ARGUMENT_FROM)            : 'bar',
                (OaiPmh.ARGUMENT_UNTIL)           : 'baz',
                (OaiPmh.ARGUMENT_SET)             : 'qux',
                (OaiPmh.ARGUMENT_RESUMPTION_TOKEN): 'quux'
        ]
        WebTarget target = this.target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.LIST_METADATA_FORMATS)
                .queryParam(OaiPmh.ARGUMENT_IDENTIFIER, identifiers as String[])
        illegal.each { target = target.queryParam(it.key, it.value) }
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
        oaiPmh.errors*.value == [error] + illegal.keySet().collect(AbstractOaiPmhServer.ERROR_ARGUMENT_ILLEGAL.&formatted)
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_ARGUMENT] * (1 + illegal.size())
        !oaiPmh.body
        where:
        identifiers    || error
        ['foo', 'bar'] || AbstractOaiPmhServer.ERROR_ARGUMENT_REPEATED.formatted(OaiPmh.ARGUMENT_IDENTIFIER)
        [':foo']       || AbstractOaiPmhServer.ERROR_ARGUMENT_INVALID.formatted(OaiPmh.ARGUMENT_IDENTIFIER, ':foo')
    }

    def 'Test list sets bad argument'() {
        given:
        final Map<String, String> illegal = [
                (OaiPmh.ARGUMENT_METADATA_PREFIX): 'foo',
                (OaiPmh.ARGUMENT_FROM)           : 'bar',
                (OaiPmh.ARGUMENT_UNTIL)          : 'baz',
                (OaiPmh.ARGUMENT_SET)            : 'qux',
                (OaiPmh.ARGUMENT_IDENTIFIER)     : 'quux'
        ]
        WebTarget target = this.target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.LIST_SETS)
                .queryParam(OaiPmh.ARGUMENT_RESUMPTION_TOKEN, ['foo', 'bar'] as String[])
        illegal.each { target = target.queryParam(it.key, it.value) }
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
        oaiPmh.errors*.value == [AbstractOaiPmhServer.ERROR_ARGUMENT_REPEATED.formatted(OaiPmh.ARGUMENT_RESUMPTION_TOKEN)] + illegal.keySet().collect(AbstractOaiPmhServer.ERROR_ARGUMENT_ILLEGAL.&formatted)
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_ARGUMENT] * (1 + illegal.size())
        !oaiPmh.body
    }

    def 'Test list identifiers bad argument'() {
        given:
        WebTarget target = this.target.queryParam(OaiPmh.ARGUMENT_VERB, Verb.LIST_IDENTIFIERS)
                .queryParam(OaiPmh.ARGUMENT_METADATA_PREFIX, prefixes as String[])
                .queryParam(OaiPmh.ARGUMENT_FROM, froms as String[])
                .queryParam(OaiPmh.ARGUMENT_UNTIL, untils as String[])
                .queryParam(OaiPmh.ARGUMENT_SET, sets as String[])
                .queryParam(OaiPmh.ARGUMENT_RESUMPTION_TOKEN, tokens as String[])
                .queryParam(OaiPmh.ARGUMENT_IDENTIFIER, 'foo')
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
        oaiPmh.errors*.value == [error, AbstractOaiPmhServer.ERROR_ARGUMENT_ILLEGAL.formatted(OaiPmh.ARGUMENT_IDENTIFIER)]
        oaiPmh.errors*.code == [OaiPmhErrorCode.BAD_ARGUMENT] * (1 + illegal.size())
        !oaiPmh.body
        where:
        prefixes       | froms          | untils            | sets                 | tokens         || error
        []             | []             | []                | []                   | []             || AbstractOaiPmhServer.ERROR_ARGUMENT_MISSING_EXCLUSIVE.formatted(OaiPmh.ARGUMENT_METADATA_PREFIX, OaiPmh.ARGUMENT_RESUMPTION_TOKEN)
        ['bar']        | []             | []                | []                   | ['baz']        || AbstractOaiPmhServer.ERROR_ARGUMENT_EXCLUSIVE.formatted(OaiPmh.ARGUMENT_METADATA_PREFIX, OaiPmh.ARGUMENT_RESUMPTION_TOKEN)
        ['bar', 'baz'] | []             | []                | []                   | []             || AbstractOaiPmhServer.ERROR_ARGUMENT_REPEATED.formatted(OaiPmh.ARGUMENT_METADATA_PREFIX)
        ['bar']        | ['baz', 'qux'] | ['quux', 'corge'] | ['grault', 'garply'] | []             || ''
        ['bar']        | ['baz']        | ['qux']           | []                   | []             || ''
        []             | []             | []                | []                   | ['bar', 'baz'] || AbstractOaiPmhServer.ERROR_ARGUMENT_REPEATED.formatted(OaiPmh.ARGUMENT_RESUMPTION_TOKEN)
    }

    // TODO empty strings should be invalid
}
