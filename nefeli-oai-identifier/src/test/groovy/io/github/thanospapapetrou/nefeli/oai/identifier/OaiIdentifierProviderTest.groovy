package io.github.thanospapapetrou.nefeli.oai.identifier

import io.github.thanospapapetrou.nefeli.oai.identifier.jaxb.OaiIdentifierAdapter
import spock.lang.Specification

class OaiIdentifierProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final OaiIdentifierProvider result = new OaiIdentifierProvider()
        then:
        result
        result.@adapter == OaiIdentifierAdapter
    }
}
