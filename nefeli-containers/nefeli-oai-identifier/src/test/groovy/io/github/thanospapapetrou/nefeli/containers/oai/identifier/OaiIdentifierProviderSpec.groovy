package io.github.thanospapapetrou.nefeli.containers.oai.identifier

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier
import spock.lang.Specification

class OaiIdentifierProviderSpec extends Specification {
    def 'Test constructor'() {
        when:
        final OaiIdentifierProvider result = new OaiIdentifierProvider()
        then:
        result
        result.container == OaiIdentifier
    }
}
