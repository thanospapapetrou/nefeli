package io.github.thanospapapetrou.nefeli.containers.oai.identifier.cdi

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier
import spock.lang.Specification

class BeansTest extends Specification {
    private final Beans beans = new Beans()

    def 'Test marshaller'() {
        given:
        final JAXBContext context = Mock()
        final Marshaller marshaller = Mock()
        context.createMarshaller() >> marshaller
        expect:
        beans.getMarshaller(context) == marshaller
    }

    def 'Test unmarshaller'() {
        given:
        final JAXBContext context = Mock()
        final Unmarshaller unmarshaller = Mock()
        context.createUnmarshaller() >> unmarshaller
        expect:
        beans.getUnmarshaller(context) == unmarshaller
    }

    def 'Test context'() {
        when:
        final JAXBContext result = beans.context
        then:
        result
        result.classes == [OaiIdentifier]
    }
}
