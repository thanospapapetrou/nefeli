package io.github.thanospapapetrou.nefeli.containers.toolkit

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit
import spock.lang.Specification

class ToolkitProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final ToolkitProvider result = new ToolkitProvider()
        then:
        result
        result.container == Toolkit
    }
}
