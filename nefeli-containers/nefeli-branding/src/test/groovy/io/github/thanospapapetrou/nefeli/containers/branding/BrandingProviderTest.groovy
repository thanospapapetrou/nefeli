package io.github.thanospapapetrou.nefeli.containers.branding

import io.github.thanospapapetrou.nefeli.containers.branding.jaxb.BrandingAdapter
import spock.lang.Specification

class BrandingProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final BrandingProvider result = new BrandingProvider()
        then:
        result
        result.@adapter == BrandingAdapter
    }
}