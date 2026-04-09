package io.github.thanospapapetrou.nefeli.oai.identifier.jaxb;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OaiIdentifierAdapter extends XmlAdapter<Element, OaiIdentifier> {
    private final DocumentBuilder builder;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    @Inject
    public OaiIdentifierAdapter(final DocumentBuilder builder, final Marshaller marshaller,
            final Unmarshaller unmarshaller) {
        this.builder = builder;
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @Override
    public Element marshal(final OaiIdentifier identifier) throws JAXBException {
        final Document document = builder.newDocument();
        marshaller.marshal(identifier, document);
        return document.getDocumentElement();
    }

    @Override
    public OaiIdentifier unmarshal(final Element element) throws JAXBException {
        return unmarshaller.unmarshal(element, OaiIdentifier.class).getValue();
    }
}
