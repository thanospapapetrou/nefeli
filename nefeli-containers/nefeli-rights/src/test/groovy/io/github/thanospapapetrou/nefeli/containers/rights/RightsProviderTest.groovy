package io.github.thanospapapetrou.nefeli.containers.rights

import org.openarchives.oai._2_0.rights.Rights
import spock.lang.Specification

class RightsProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final RightsProvider result = new RightsProvider()
        then:
        result
        result.container == Rights
    }
}
