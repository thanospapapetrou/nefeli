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
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.jaxb.adapters.InstantStringAdapter;

public class OaiPmhReader implements MessageBodyReader<OaiPmhResponse> {
    private final DocumentBuilder builder;

    @Inject
    public OaiPmhReader(final DocumentBuilder builder) {
        this.builder = builder;
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
            final Document document = builder.parse(body);
            final Unmarshaller unmarshaller = JAXBContext.newInstance(OaiPmhResponse.class).createUnmarshaller();
            unmarshaller.setAdapter(InstantStringAdapter.class,
                    new InstantStringAdapter(Granularity.YYYY_MM_DD_THH_MM_SS_Z)); // TODO switch granularity
            return unmarshaller.unmarshal(document.getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
