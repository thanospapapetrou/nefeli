package io.github.thanospapapetrou.nefeli.containers.toolkit

import io.github.thanospapapetrou.nefeli.containers.toolkit.jaxb.ToolkitAdapter
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