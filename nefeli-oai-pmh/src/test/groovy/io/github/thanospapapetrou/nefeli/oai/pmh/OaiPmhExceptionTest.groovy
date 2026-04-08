package io.github.thanospapapetrou.nefeli.oai.pmh

import org.openarchives.oai._2.OaiPmhError
import spock.lang.Specification
import spock.lang.Unroll

class OaiPmhExceptionTest extends Specification {
    private static final String ERROR = 'error'
    private static final String OTHER_ERROR = 'other error'

    def 'Test constructor'() {
        given:
        final List<OaiPmhError> errors = Mock(List)
        when:
        final OaiPmhException e = new OaiPmhException(errors)
        then:
        e
        e.errors == errors
    }

    @Unroll('Test get message (errors: #errors)')
    def 'Test get message'() {
        given:
        final OaiPmhException e = new OaiPmhException(errors)
        expect:
        e.message == expected
        where:
        errors                                                                                       || expected
        []                                                                                           || ''
        [Mock(OaiPmhError) { toString() >> ERROR }]                                                  || ERROR
        [Mock(OaiPmhError) { toString() >> ERROR }, Mock(OaiPmhError) { toString() >> OTHER_ERROR }] || ERROR + OaiPmhException.DELIMITER + OTHER_ERROR
    }
}
