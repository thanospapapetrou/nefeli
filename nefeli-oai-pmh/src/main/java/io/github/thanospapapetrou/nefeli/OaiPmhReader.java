package io.github.thanospapapetrou.nefeli;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.OaiPmhResponse;
import org.xml.sax.SAXException;

public class OaiPmhReader implements MessageBodyReader<OaiPmhResponse> {
    private final DocumentBuilder builder;
    private final Unmarshaller unmarshaller;

    @Inject
    public OaiPmhReader(final DocumentBuilder builder, final Unmarshaller unmarshaller) {
        this.builder = builder;
        this.unmarshaller = unmarshaller;
    }

    @Override
    public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

    @Override
    public OaiPmhResponse readFrom(final Class<OaiPmhResponse> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType, final MultivaluedMap<String, String> headers, final InputStream body)
            throws IOException, WebApplicationException {

        try {
            return unmarshaller.unmarshal(builder.parse(body).getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
