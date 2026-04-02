package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.URI;
import java.net.URL;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.openarchives.oai._2.Container;

public interface ContainerProvider {
    URI getNamespace();

    String getLocalName();

    String getPrefix();

    URL getSchema();

    Class<? extends Container> getContainerClass();

    Marshaller getMarshaller() throws JAXBException;

    Unmarshaller getUnmarshaller() throws JAXBException;
}
