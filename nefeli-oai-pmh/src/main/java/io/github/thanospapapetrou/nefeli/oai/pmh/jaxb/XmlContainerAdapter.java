package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.namespace.QName;

import org.openarchives.oai._2.GenericXmlContent;
import org.openarchives.oai._2.XmlContent;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentProvider;

@ApplicationScoped
public class XmlContainerAdapter extends XmlAdapter<Element, XmlContent> {
    private static final Logger LOGGER = Logger.getLogger(XmlContainerAdapter.class.getName());
    private static final String ERROR_MARSHALLING = "Error marshalling %1$s";
    private static final String ERROR_NO_CONTENT_PROVIDER_CLASS = "No content provider found for class %1$s";
    private static final String ERROR_NO_CONTENT_PROVIDER_ELEMENT =
            "No content provider found for element %1$s, falling back to generic content";
    private static final String ERROR_UNMARSHALLING = "Error unmarshalling %1$s";

    @Override
    public Element marshal(final XmlContent element) throws JAXBException {
        if (element == null) {
            return null;
        }
        if (element instanceof GenericXmlContent generic) {
            return generic.getElement();
        }
        final ServiceLoader.Provider<XmlContentProvider> provider = getProvider(element);
        if (provider == null) {
            LOGGER.warning(String.format(ERROR_NO_CONTENT_PROVIDER_CLASS, element.getClass().getName()));
            return null;
        }
        try {
            return (Element) provider.get().getAdapter().marshal(element);
        } catch (final Exception e) {
            throw new JAXBException(String.format(ERROR_MARSHALLING, element.getClass().getName()), e);
        }
    }

    @Override
    public XmlContent unmarshal(final Element element) throws JAXBException {
        if (element == null) {
            return null;
        }
        final ServiceLoader.Provider<XmlContentProvider> provider = getProvider(element);
        if (provider == null) {
            LOGGER.warning(String.format(ERROR_NO_CONTENT_PROVIDER_ELEMENT, new QName(element.getNamespaceURI(),
                    element.getLocalName())));
            return new GenericXmlContent(element);
        }
        try {
            return ((XmlContent) provider.get().getAdapter().unmarshal(element));
        } catch (final Exception e) {
            throw new JAXBException(String.format(ERROR_UNMARSHALLING,
                    new QName(element.getNamespaceURI(), element.getLocalName())), e);
        }
    }

    private ServiceLoader.Provider<XmlContentProvider> getProvider(final XmlContent element) {
        return ServiceLoader.load(XmlContentProvider.class).stream()
                .filter(p -> XmlContentProvider.getXmlContentClass((Class<? extends XmlContentProvider<?>>) p.type())
                        .isInstance(element))
                .findFirst().orElse(null);
    }

    private ServiceLoader.Provider<XmlContentProvider> getProvider(final Element element) {
        return ServiceLoader.load(XmlContentProvider.class).stream()
                .filter(p -> XmlContentProvider.getNamespace((Class<? extends XmlContentProvider<?>>) p.type())
                        .equals(element.getNamespaceURI())
                        && Objects.equals(XmlContentProvider.getLocalName((Class<? extends XmlContentProvider<?>>) p.type()),
                        element.getLocalName()))
                .findFirst().orElse(null);
    }
}
