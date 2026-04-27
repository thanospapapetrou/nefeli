package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;

import io.github.thanospapapetrou.nefeli.common.cdi.Beans;
import io.github.thanospapapetrou.nefeli.common.cdi.Configuration;
import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;

@Provider
@Produces(OaiPmhResponse.CONTENT_TYPE)
public class OaiPmhWriter implements MessageBodyWriter<OaiPmhResponse<OaiPmhBody>> {
    private static final String ERROR_WRITING = "Error writing OAI-PMH response";

    private final Marshaller marshaller;

    @Inject
    public OaiPmhWriter(@Beans.Jaxb(OaiPmhResponse.class) final Marshaller marshaller,
            @Configuration.Property("nefeli.oai-pmh.server.granularity") final Granularity granularity) {
        this(marshaller);
        marshaller.setAdapter(InstantStringAdapter.class, new InstantStringAdapter(granularity));
    }

    private OaiPmhWriter(final Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(final OaiPmhResponse<OaiPmhBody> response, final Class<?> clazz,
            final Type type, final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, Object> headers, final OutputStream body) throws IOException {
        try {
            marshaller.marshal(response, body);
        } catch (final JAXBException e) {
            throw new IOException(ERROR_WRITING, e);
        }
    }
}
