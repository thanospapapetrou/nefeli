package io.github.thanospapapetrou.nefeli.oai.identifier.jaxb

import jakarta.xml.bind.JAXBElement
import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier
import org.w3c.dom.Document
import org.w3c.dom.Element
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder

class OaiIdentifierAdapterTest extends Specification {
    private OaiIdentifierAdapter adapter

    def setup() {
        adapter = new OaiIdentifierAdapter(Mock(DocumentBuilder), Mock(Marshaller), Mock(Unmarshaller))
    }

    def 'Test marshal'() {
        given:
        final OaiIdentifier identifier = Mock()
        final Document document = Mock()
        adapter.builder.newDocument() >> document
        final Element element = Mock()
        document.getDocumentElement() >> element
        when:
        final Element result = adapter.marshal(identifier)
        then:
        result == element
        1 * adapter.marshaller.marshal(identifier, document)
    }

    def 'Test unmarshal'() {
        given:
        final Element element = Mock()
        final JAXBElement<OaiIdentifier> jaxb = Mock()
        adapter.unmarshaller.unmarshal(element, OaiIdentifier) >> jaxb
        final OaiIdentifier identifier = Mock()
        jaxb.value >> identifier
        expect:
        adapter.unmarshal(element) == identifier
    }
}
