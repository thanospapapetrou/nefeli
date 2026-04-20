package io.github.thanospapapetrou.nefeli.containers.rights

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper
import org.openarchives.oai._2_0.rights.Rights
import spock.lang.Specification

class RightsProviderTest extends Specification {
    def 'Test constructor'() {
        given:
        final XmlHelper xml = Mock()
        final JaxbHelper jaxb = Mock()
        when:
        final RightsProvider result = new RightsProvider(xml, jaxb)
        then:
        result
        result.xml == xml
        result.jaxb == jaxb
        result.container == Rights
    }
}