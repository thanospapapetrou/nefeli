package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import jakarta.xml.bind.JAXBException;

import javax.xml.parsers.ParserConfigurationException;

import org.openarchives.oai._2.Container;
import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.ContainerAdapter;

public abstract class ContainerProvider<T extends Container> {
    protected final XmlHelper xml;
    protected final JaxbHelper jaxb;
    protected final Class<T> container;

    protected ContainerProvider(final XmlHelper xml, final JaxbHelper jaxb, final Class<T> container) {
        this.xml = xml;
        this.jaxb = jaxb;
        this.container = container;
    }

    public ContainerAdapter<T> getAdapter()
            throws JAXBException, MalformedURLException, ParserConfigurationException, SAXException,
            URISyntaxException {
        return new ContainerAdapter<>(xml.getBuilder(jaxb.getSchema(container)), jaxb.getMarshaller(container),
                jaxb.getUnmarshaller(container), container);
    }
}
