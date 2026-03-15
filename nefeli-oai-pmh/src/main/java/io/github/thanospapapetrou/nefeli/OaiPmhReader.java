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
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhResponse;

public class OaiPmhReader implements MessageBodyReader<OaiPmhResponse> {
    @Override
    public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

    @Override
    public OaiPmhResponse readFrom(final Class<OaiPmhResponse> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, String> headers, final InputStream body)
            throws IOException, WebApplicationException {

        try {
            final Unmarshaller unmarshaller = JAXBContext.newInstance(OaiPmhResponse.class).createUnmarshaller();
            unmarshaller.setAdapter(InstantStringAdapter.class,
                    new InstantStringAdapter(Granularity.YYYY_MM_DD_THH_MM_SS_Z)); // TODO
            // switch granularity
            // TODO set schema
            return (OaiPmhResponse) unmarshaller.unmarshal(body);
        } catch (final JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
