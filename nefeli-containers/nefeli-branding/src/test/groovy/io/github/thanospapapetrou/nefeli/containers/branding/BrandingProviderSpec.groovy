package io.github.thanospapapetrou.nefeli.containers.branding

import org.openarchives.oai._2_0.branding.Branding
import spock.lang.Specification

class BrandingProviderSpec extends Specification {
    def 'Test constructor'() {
        when:
        final BrandingProvider result = new BrandingProvider()
        then:
        result
        result.container == Branding
    }
}
