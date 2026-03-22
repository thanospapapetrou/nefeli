package io.github.thanospapapetrou.nefeli;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.xml.sax.SAXException;

@Consumes
@Provider
public class OaiPmhReader<T extends OaiPmhBody> implements MessageBodyReader<OaiPmhResponse<T>> {
    private static final Logger LOGGER = Logger.getLogger(OaiPmhReader.class.getName());
    private static final String WARNING_INVALID_MEDIA_TYPE = "OAI-PMH response has invalid media type %1$s";
    private static final String WARNING_NO_CHARACTER_SET = "OAI-PMH response doesn't specify any character set";
    private static final String WARNING_INVALID_CHARACTER_SET = "OAI-PMH response has invalid character set %1$s";

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
        if ((!mediaType.getType().equals(MediaType.TEXT_XML_TYPE.getType()))
                || (!mediaType.getSubtype().equals(MediaType.TEXT_XML_TYPE.getSubtype()))) {
            LOGGER.warning(String.format(WARNING_INVALID_MEDIA_TYPE, mediaType));
        }
        if (!mediaType.getParameters().containsKey(MediaType.CHARSET_PARAMETER)) {
            LOGGER.warning(WARNING_NO_CHARACTER_SET);
        }
        if (!mediaType.getParameters().get(MediaType.CHARSET_PARAMETER)
                .equalsIgnoreCase(StandardCharsets.UTF_8.name())) {
            LOGGER.warning(String.format(WARNING_INVALID_CHARACTER_SET,
                    mediaType.getParameters().get(MediaType.CHARSET_PARAMETER)));
        }
        return true;
    }

    @Override
    public OaiPmhResponse<T> readFrom(final Class<OaiPmhResponse<T>> clazz, final Type type,
            final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> headers,
            final InputStream body) throws IOException, WebApplicationException {

        try {
            return unmarshaller.unmarshal(builder.parse(body).getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
