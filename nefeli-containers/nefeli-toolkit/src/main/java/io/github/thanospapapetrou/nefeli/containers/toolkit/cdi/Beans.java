package io.github.thanospapapetrou.nefeli.containers.toolkit.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;

@ApplicationScoped
public class Beans {
    @Named("toolkitMarshaller")
    @Produces
    public Marshaller getMarshaller(@Named("toolkitContext") final JAXBContext context) throws JAXBException {
        return context.createMarshaller();
    }

    @Named("toolkitUnmarshaller")
    @Produces
    public Unmarshaller getUnmarshaller(@Named("toolkitContext") final JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }

    @ApplicationScoped
    @Named("toolkitContext")
    @Produces
    public JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(Toolkit.class);
    }
}
