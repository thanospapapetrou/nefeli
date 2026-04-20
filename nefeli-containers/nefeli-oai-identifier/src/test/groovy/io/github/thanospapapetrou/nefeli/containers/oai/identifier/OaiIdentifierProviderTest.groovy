package io.github.thanospapapetrou.nefeli.containers.oai.identifier

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper
import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier
import spock.lang.Specification

class OaiIdentifierProviderTest extends Specification {
    def 'Test constructor'() {
        given:
        final XmlHelper xml = Mock()
        final JaxbHelper jaxb = Mock()
        when:
        final OaiIdentifierProvider result = new OaiIdentifierProvider(xml, jaxb)
        then:
        result
        result.xml == xml
        result.jaxb == jaxb
        result.container == OaiIdentifier
    }
}
