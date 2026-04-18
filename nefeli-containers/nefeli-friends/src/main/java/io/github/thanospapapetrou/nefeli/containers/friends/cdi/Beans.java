package io.github.thanospapapetrou.nefeli.containers.friends.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.openarchives.oai._2_0.friends.Friends;

@ApplicationScoped
public class Beans {
    @Named("friendsMarshaller")
    @Produces
    public Marshaller getMarshaller(@Named("friendsContext") final JAXBContext context) throws JAXBException {
        return context.createMarshaller();
    }

    @Named("friendsUnmarshaller")
    @Produces
    public Unmarshaller getUnmarshaller(@Named("friendsContext") final JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }

    @ApplicationScoped
    @Named("friendsContext")
    @Produces
    public JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(Friends.class);
    }
}
