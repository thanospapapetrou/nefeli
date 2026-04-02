package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;

import org.openarchives.oai._2.Container;

public abstract class AbstractContainerProvider implements ContainerProvider {
    private final URI namespace;
    private final String localName;
    private final String prefix;
    private final URL schema;
    private final Class<? extends Container> containerClass;
    private final JAXBContext context;

    protected AbstractContainerProvider(final Class<? extends Container> containerClass) throws JAXBException,
            MalformedURLException, URISyntaxException {
        this(new URI(containerClass.getAnnotation(XmlRootElement.class).namespace()),
                containerClass.getAnnotation(XmlRootElement.class).name(),
                containerClass.getPackage().getAnnotation(XmlSchema.class).xmlns()[0].prefix(),
                new URI(containerClass.getPackage().getAnnotation(XmlSchema.class).location()).toURL(),
                containerClass, JAXBContext.newInstance(containerClass));
    }

    private AbstractContainerProvider(final URI namespace, final String localName, final String prefix,
            final URL schema, final Class<? extends Container> containerClass, final JAXBContext context) {
        this.namespace = namespace;
        this.localName = localName;
        this.prefix = prefix;
        this.schema = schema;
        this.containerClass = containerClass;
        this.context = context;
    }

    @Override
    public URI getNamespace() {
        return namespace;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public URL getSchema() {
        return schema;
    }

    @Override
    public Class<? extends Container> getContainerClass() {
        return containerClass;
    }

    @Override
    public Marshaller getMarshaller() throws JAXBException {
        return context.createMarshaller();
    }

    @Override
    public Unmarshaller getUnmarshaller() throws JAXBException {
        return context.createUnmarshaller();
    }
}
