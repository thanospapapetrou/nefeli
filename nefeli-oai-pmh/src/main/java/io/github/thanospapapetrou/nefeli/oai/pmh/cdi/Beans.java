package io.github.thanospapapetrou.nefeli.oai.pmh.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import org.openarchives.oai._2.DeletedRecord;
import org.openarchives.oai._2.Granularity;

import io.github.thanospapapetrou.nefeli.common.cdi.Configuration;

@ApplicationScoped
public class Beans {
    @Produces
    public Client getClient() {
        return ClientBuilder.newClient();
    }

    @Configuration.Property
    @Produces
    public DeletedRecord getDeletedRecord(final InjectionPoint point) {
        return CDI.current().select(Configuration.class).get().getEnumeration(point);
    }

    @Configuration.Property
    @Produces
    public Granularity getGranularity(final InjectionPoint point) {
        return CDI.current().select(Configuration.class).get().getEnumeration(point);
    }
}
