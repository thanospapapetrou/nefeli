package io.github.thanospapapetrou.nefeli.containers.branding

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper
import org.openarchives.oai._2_0.branding.Branding
import spock.lang.Specification

class BrandingProviderTest extends Specification {
    def 'Test constructor'() {
        final XmlHelper xml = Mock()
        final JaxbHelper jaxb = Mock()
        when:
        final BrandingProvider result = new BrandingProvider(xml, jaxb)
        then:
        result
        result.xml == xml
        result.jaxb == jaxb
        result.container == Branding
    }
}