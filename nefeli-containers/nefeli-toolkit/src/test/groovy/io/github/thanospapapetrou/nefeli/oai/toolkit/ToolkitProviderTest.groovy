package io.github.thanospapapetrou.nefeli.oai.toolkit

import io.github.thanospapapetrou.nefeli.oai.toolkit.jaxb.ToolkitAdapter
import spock.lang.Specification

class ToolkitProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final ToolkitProvider result = new ToolkitProvider()
        then:
        result
        result.@adapter == ToolkitAdapter
    }
}