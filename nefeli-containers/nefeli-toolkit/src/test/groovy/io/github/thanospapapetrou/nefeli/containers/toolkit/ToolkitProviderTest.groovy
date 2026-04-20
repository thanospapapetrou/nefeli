package io.github.thanospapapetrou.nefeli.containers.toolkit

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit
import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper
import spock.lang.Specification

class ToolkitProviderTest extends Specification {
    def 'Test constructor'() {
        given:
        final XmlHelper xml = Mock()
        final JaxbHelper jaxb = Mock()
        when:
        final ToolkitProvider result = new ToolkitProvider(xml, jaxb)
        then:
        result
        result.xml == xml
        result.jaxb == jaxb
        result.container == Toolkit
    }
}