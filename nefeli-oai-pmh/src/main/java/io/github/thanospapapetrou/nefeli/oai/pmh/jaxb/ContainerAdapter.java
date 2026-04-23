package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;

import org.openarchives.oai._2.Container;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ContainerAdapter<T extends Container> extends XmlAdapter<Element, T> {
    protected final DocumentBuilder builder;
    protected final Marshaller marshaller;
    protected final Unmarshaller unmarshaller;
    protected final Class<T> clazz;

    public ContainerAdapter(final DocumentBuilder builder, final Marshaller marshaller,
            final Unmarshaller unmarshaller, final Class<T> clazz) {
        this.builder = builder;
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
        this.clazz = clazz;
    }

    @Override
    public Element marshal(final T container) throws JAXBException {
        final Document document = builder.newDocument();
        marshaller.marshal(container, document);
        return document.getDocumentElement();
    }

    @Override
    public T unmarshal(final Element element) throws JAXBException {
        return unmarshaller.unmarshal(element, clazz).getValue();
    }
}
