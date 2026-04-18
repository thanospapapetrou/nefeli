package io.github.thanospapapetrou.nefeli.containers.branding.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.openarchives.oai._2_0.branding.Branding;

@ApplicationScoped
public class Beans {
    @Named("brandingMarshaller")
    @Produces
    public Marshaller getMarshaller(@Named("brandingContext") final JAXBContext context) throws JAXBException {
        return context.createMarshaller();
    }

    @Named("brandingUnmarshaller")
    @Produces
    public Unmarshaller getUnmarshaller(@Named("brandingContext") final JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }

    @ApplicationScoped
    @Named("brandingContext")
    @Produces
    public JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(Branding.class);
    }
}
