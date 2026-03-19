package io.github.thanospapapetrou.nefeli;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.xml.sax.SAXException;

public class OaiPmhReader<T extends OaiPmhBody> implements MessageBodyReader<OaiPmhResponse<T>> {
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
        return MediaType.TEXT_XML_TYPE.isCompatible(mediaType)
                && mediaType.getParameters().containsKey(MediaType.CHARSET_PARAMETER)
                && mediaType.getParameters().get(MediaType.CHARSET_PARAMETER)
                .equalsIgnoreCase(StandardCharsets.UTF_8.name());
    }

    @Override
    public OaiPmhResponse<T> readFrom(final Class<OaiPmhResponse<T>> clazz, final Type type,
            final Annotation[] annotations,
            final MediaType mediaType, final MultivaluedMap<String, String> headers, final InputStream body)
            throws IOException, WebApplicationException {

        try {
            return unmarshaller.unmarshal(builder.parse(body).getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
