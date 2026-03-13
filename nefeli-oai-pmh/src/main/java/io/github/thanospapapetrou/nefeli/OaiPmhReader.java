package io.github.thanospapapetrou.nefeli;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;

import org.openarchives.oai._2.OAIPMHtype;

public class OaiPmhReader implements MessageBodyReader<OAIPMHtype> {
    @Override
    public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

    @Override
    public OAIPMHtype readFrom(final Class<OAIPMHtype> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, String> headers, final InputStream body)
            throws IOException, WebApplicationException {

        try {
            return ((JAXBElement<OAIPMHtype>) JAXBContext.newInstance(OAIPMHtype.class).createUnmarshaller().unmarshal(body)).getValue();
        } catch (final JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
