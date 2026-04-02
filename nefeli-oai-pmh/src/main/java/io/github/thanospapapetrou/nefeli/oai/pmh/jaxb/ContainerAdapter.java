package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.util.ServiceLoader;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openarchives.oai._2.Container;
import org.openarchives.oai._2.GenericContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class ContainerAdapter extends XmlAdapter<Element, Container> {
    private static final Logger LOGGER = Logger.getLogger(ContainerAdapter.class.getName());

    @Override
    public Element marshal(final Container element) throws JAXBException, ParserConfigurationException {
        if (element == null) {
            return null;
        }
        final ContainerProvider provider = ServiceLoader.load(ContainerProvider.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .filter(p -> p.getContainerClass().isInstance(element))
                .findFirst().orElse(null);
        if (provider == null) {
            LOGGER.warning("No ContainerProvider found for class: " + element.getClass().getName());
            return null;
        }
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        provider.getMarshaller().marshal(element, document);
        return document.getDocumentElement();
    }

    @Override
    public Container unmarshal(final Element element) throws JAXBException {
        if (element == null) {
            return null;
        }
        final ContainerProvider provider = ServiceLoader.load(ContainerProvider.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .filter(p -> p.getNamespace().toString().equals(element.getNamespaceURI())
                        && p.getLocalName().equals(element.getLocalName()))
                .findFirst().orElse(null);
        if (provider == null) {
            return new GenericContainer(element);
        }
        return provider.getUnmarshaller().unmarshal(element, provider.getContainerClass()).getValue();
    }
}
