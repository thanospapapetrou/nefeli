package io.github.thanospapapetrou.nefeli.oai.identifier.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

@ApplicationScoped
public class Beans {
    @Named("oaiIdentifierMarshaller")
    @Produces
    public Marshaller getMarshaller(@Named("oaiIdentifierContext") final JAXBContext context) throws JAXBException {
        return context.createMarshaller();
    }

    @Named("oaiIdentifierUnmarshaller")
    @Produces
    public Unmarshaller getUnmarshaller(@Named("oaiIdentifierContext") final JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }

    @ApplicationScoped
    @Named("oaiIdentifierContext")
    @Produces
    public JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(OaiIdentifier.class);
    }
}
