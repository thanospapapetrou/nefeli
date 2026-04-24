package io.github.thanospapapetrou.nefeli.oai.pmh;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlSchema;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.Container;

import io.github.thanospapapetrou.nefeli.common.cdi.Beans;
import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.ContainerAdapter;

public abstract class ContainerProvider<T extends Container> {
    protected final Class<T> container;

    protected ContainerProvider(final Class<T> container) {
        this.container = container;
    }

    public ContainerAdapter<T> getAdapter() {
        final Beans.Jaxb jaxb = new Beans.Jaxb.Literal(container);
        return new ContainerAdapter<>(CDI.current().select(DocumentBuilder.class,
                new Beans.Xsd.Literal(jaxb.value().getPackage().getAnnotation(XmlSchema.class).location())).get(),
                CDI.current().select(Marshaller.class, jaxb).get(),
                CDI.current().select(Unmarshaller.class, jaxb).get(), container);
    }
}
