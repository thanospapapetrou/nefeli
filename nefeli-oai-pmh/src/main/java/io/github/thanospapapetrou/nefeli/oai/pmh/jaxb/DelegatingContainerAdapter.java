package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.openarchives.oai._2.Container;
import org.openarchives.oai._2.GenericContainer;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class DelegatingContainerAdapter extends ContainerAdapter<Container> {
    private static final Logger LOGGER = Logger.getLogger(DelegatingContainerAdapter.class.getName());
    private static final String ERROR_INITIALIZING_ADAPTER_CLASS =
            "Error initializing container adapter for class %1$s";
    private static final String ERROR_INITIALIZING_ADAPTER_ELEMENT =
            "Error initializing container adapter for element %1$s";
    private static final String ERROR_NO_CONTAINER_PROVIDER_CLASS = "No container provider found for class %1$s";
    private static final String ERROR_NO_CONTAINER_PROVIDER_ELEMENT =
            "No container provider found for element %1$s, falling back to generic container";
    private static final String ERROR_UNMARSHALLING = "Error unmarshalling %1$s";

    public DelegatingContainerAdapter() {
        super(null, null, null, null);
    }

    @Override
    public Element marshal(final Container container) throws JAXBException {
        if (container instanceof GenericContainer generic) {
            return generic.getElement();
        }
        final Optional<ContainerProvider<Container>> provider = getProvider(container).map(ServiceLoader.Provider::get);
        if (provider.isEmpty()) {
            throw new JAXBException(String.format(ERROR_NO_CONTAINER_PROVIDER_CLASS, container.getClass().getName()));
        }
        try {
            return provider.get().getAdapter().marshal(container);
        } catch (final MalformedURLException | ParserConfigurationException | URISyntaxException | SAXException e) {
            throw new JAXBException(String.format(ERROR_INITIALIZING_ADAPTER_CLASS, container.getClass().getName()), e);
        }
    }

    @Override
    public Container unmarshal(final Element element) throws JAXBException {
        final Optional<ContainerProvider<Container>> provider = getProvider(element).map(ServiceLoader.Provider::get);
        if (provider.isEmpty()) {
            LOGGER.warning(String.format(ERROR_NO_CONTAINER_PROVIDER_ELEMENT, new QName(element.getNamespaceURI(),
                    element.getLocalName())));
            return new GenericContainer(element);
        }
        try {
            final ContainerAdapter<Container> adapter = provider.get().getAdapter();
            try {
                return adapter.unmarshal(element);
            } catch (final JAXBException e) {
                LOGGER.warning(String.format(ERROR_UNMARSHALLING, new QName(element.getNamespaceURI(),
                        element.getLocalName())));
                return new GenericContainer(element);
            }
        } catch (final MalformedURLException | ParserConfigurationException | URISyntaxException | SAXException e) {
            throw new JAXBException(String.format(ERROR_INITIALIZING_ADAPTER_ELEMENT,
                    new QName(element.getNamespaceURI(), element.getLocalName())), e);
        }
    }

    private Optional<ServiceLoader.Provider<ContainerProvider>> getProvider(final Container container) {
        return ServiceLoader.load(ContainerProvider.class).stream()
                .filter(p -> getContainerClass(p).isInstance(container)).findFirst();
    }

    private Optional<ServiceLoader.Provider<ContainerProvider>> getProvider(final Element element) {
        return ServiceLoader.load(ContainerProvider.class).stream()
                .filter(p -> getContainerNamespace(p).equals(element.getNamespaceURI())
                        && getContainerName(p).equals(element.getLocalName())).findFirst();
    }

    private Class<?> getContainerClass(final ServiceLoader.Provider<ContainerProvider> provider) {
        return (Class<?>) ((ParameterizedType) provider.type().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getContainerNamespace(final ServiceLoader.Provider<ContainerProvider> provider) {
        return getContainerClass(provider).getAnnotation(XmlRootElement.class).namespace();
    }

    private String getContainerName(final ServiceLoader.Provider<ContainerProvider> provider) {
        return getContainerClass(provider).getAnnotation(XmlRootElement.class).name();
    }
}
