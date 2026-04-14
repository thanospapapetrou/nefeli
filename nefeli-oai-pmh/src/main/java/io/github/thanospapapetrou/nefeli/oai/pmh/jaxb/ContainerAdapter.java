package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.util.List;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.namespace.QName;

import org.openarchives.oai._2.GenericContent;
import org.openarchives.oai._2.XmlContent;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContentProvider;

@ApplicationScoped
public class ContainerAdapter extends XmlAdapter<Element, XmlContent> {
    private static final Logger LOGGER = Logger.getLogger(ContainerAdapter.class.getName());
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
        if (element instanceof GenericContent generic) {
            return generic.getElement();
        }
        final ServiceLoader.Provider<ContentProvider> provider = getProvider(element);
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
        final ServiceLoader.Provider<ContentProvider> provider = getProvider(element);
        if (provider == null) {
            LOGGER.warning(String.format(ERROR_NO_CONTENT_PROVIDER_ELEMENT, new QName(element.getNamespaceURI(),
                    element.getLocalName())));
            return new GenericContent(element);
        }
        try {
            return ((XmlContent) provider.get().getAdapter().unmarshal(element));
        } catch (final Exception e) {
            throw new JAXBException(String.format(ERROR_UNMARSHALLING,
                    new QName(element.getNamespaceURI(), element.getLocalName())), e);
        }
    }

    private ServiceLoader.Provider<ContentProvider> getProvider(final XmlContent element) {
        return ServiceLoader.load(ContentProvider.class).stream()
                .filter(p -> ContentProvider.getContentClass((Class<? extends ContentProvider<?>>) p.type())
                        .isInstance(element))
                .findFirst().orElse(null);
    }

    private ServiceLoader.Provider<ContentProvider> getProvider(final Element element) {
        try {
            final List<ServiceLoader.Provider<ContentProvider>> providers =
                    ServiceLoader.load(ContentProvider.class).stream().toList();
            LOGGER.info("Providers: " + providers.size());
        } catch (final ServiceConfigurationError e) {
            LOGGER.log(Level.SEVERE, "Error loading providers", e);
        }
        return ServiceLoader.load(ContentProvider.class).stream()
                .filter(p -> ContentProvider.getNamespace((Class<? extends ContentProvider<?>>) p.type())
                        .equals(element.getNamespaceURI())
                        && Objects.equals(ContentProvider.getLocalName((Class<? extends ContentProvider<?>>) p.type()),
                        element.getLocalName()))
                .findFirst().orElse(null);
    }
}
