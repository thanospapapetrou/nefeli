package io.github.thanospapapetrou.nefeli.oai.identifier.jaxb


import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder

class OaiIdentifierAdapterTest extends Specification {
    def 'Test constructor'() {
        given:
        final DocumentBuilder builder = Mock()
        final Marshaller marshaller = Mock()
        final Unmarshaller unmarshaller = Mock()
        when:
        final OaiIdentifierAdapter result = new OaiIdentifierAdapter(builder, marshaller, unmarshaller)
        then:
        result
        result.builder == builder
        result.marshaller == marshaller
        result.unmarshaller == unmarshaller
        result.clazz == OaiIdentifier
    }
}
