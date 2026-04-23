package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.lang.reflect.ParameterizedType;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.namespace.QName;

import org.openarchives.oai._2.Container;
import org.openarchives.oai._2.GenericContainer;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class DelegatingContainerAdapter extends XmlAdapter<Element, Container> {
    private static final Logger LOGGER = Logger.getLogger(DelegatingContainerAdapter.class.getName());
    private static final String ERROR_MARSHALLING = "Error marshalling %1$s";
    private static final String ERROR_NO_CONTAINER_PROVIDER_CLASS = "No container provider found for class %1$s";
    private static final String ERROR_NO_CONTAINER_PROVIDER_ELEMENT =
            "No container provider found for element %1$s, falling back to generic container";
    private static final String ERROR_UNMARSHALLING = "Error unmarshalling %1$s";

    @Override
    public Element marshal(final Container container) throws JAXBException {
        if (container == null) {
            return null;
        }
        if (container instanceof GenericContainer generic) {
            return generic.getElement();
        }
        final ServiceLoader.Provider<ContainerProvider> provider = getProvider(container);
        if (provider == null) {
            LOGGER.warning(String.format(ERROR_NO_CONTAINER_PROVIDER_CLASS, container.getClass().getName()));
            return null;
        }
        try {
            return provider.get().getAdapter().marshal(container);
        } catch (final Exception e) { // TODO
            throw new JAXBException(String.format(ERROR_MARSHALLING, container.getClass().getName()), e);
        }
    }

    @Override
    public Container unmarshal(final Element element) throws JAXBException {
        if (element == null) {
            return null;
        }
        final ServiceLoader.Provider<ContainerProvider> provider = getProvider(element);
        if (provider == null) {
            LOGGER.warning(String.format(ERROR_NO_CONTAINER_PROVIDER_ELEMENT, new QName(element.getNamespaceURI(),
                    element.getLocalName())));
            return new GenericContainer(element);
        }
        try {
            return provider.get().getAdapter().unmarshal(element);
        } catch (final Exception e) { // TODO
            throw new JAXBException(String.format(ERROR_UNMARSHALLING,
                    new QName(element.getNamespaceURI(), element.getLocalName())), e);
        }
    }

    private ServiceLoader.Provider<ContainerProvider> getProvider(final Container container) {
        return ServiceLoader.load(ContainerProvider.class).stream()
                .filter(p -> getContainerClass(p).isInstance(container))
                .findFirst().orElse(null);
    }

    private ServiceLoader.Provider<ContainerProvider> getProvider(final Element element) {
        return ServiceLoader.load(ContainerProvider.class).stream()
                .filter(p -> getContainerNamespace(p).equals(element.getNamespaceURI())
                        && getContainerName(p).equals(element.getLocalName()))
                .findFirst().orElse(null);
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
