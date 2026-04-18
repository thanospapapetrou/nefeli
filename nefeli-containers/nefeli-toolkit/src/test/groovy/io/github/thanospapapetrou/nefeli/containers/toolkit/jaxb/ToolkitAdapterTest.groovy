package io.github.thanospapapetrou.nefeli.containers.toolkit.jaxb

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit
import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder

class ToolkitAdapterTest extends Specification {
    def 'Test constructor'() {
        given:
        final DocumentBuilder builder = Mock()
        final Marshaller marshaller = Mock()
        final Unmarshaller unmarshaller = Mock()
        when:
        final ToolkitAdapter result = new ToolkitAdapter(builder, marshaller, unmarshaller)
        then:
        result
        result.builder == builder
        result.marshaller == marshaller
        result.unmarshaller == unmarshaller
        result.clazz == Toolkit
    }
}