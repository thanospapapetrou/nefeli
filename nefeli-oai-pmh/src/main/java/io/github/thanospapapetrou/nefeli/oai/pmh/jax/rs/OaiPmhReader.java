package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;
import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;

@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Provider
public class OaiPmhReader<T extends OaiPmhBody> implements MessageBodyReader<OaiPmhResponse<T>> {
    private static final Logger LOGGER = Logger.getLogger(OaiPmhReader.class.getName());
    private static final String WARNING_INVALID_MEDIA_TYPE = "OAI-PMH response has invalid media type %1$s";

    private final DocumentBuilder builder;
    private final Unmarshaller unmarshaller;

    public OaiPmhReader(final Granularity granularity) {
        this(CDI.current().select(DocumentBuilder.class).get(), CDI.current().select(Unmarshaller.class).get(),
                granularity);
    }

    private OaiPmhReader(final DocumentBuilder builder, final Unmarshaller unmarshaller,
            final Granularity granularity) {
        this(builder, unmarshaller);
        this.unmarshaller.setAdapter(InstantStringAdapter.class, new InstantStringAdapter(granularity));
    }

    private OaiPmhReader(final DocumentBuilder builder, final Unmarshaller unmarshaller) {
        this.builder = builder;
        this.unmarshaller = unmarshaller;
    }

    @Override
    public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        // TODO include URL
        if (!mediaType.equals(MediaType.TEXT_XML_TYPE.withCharset(StandardCharsets.UTF_8.name()))) {
            LOGGER.warning(String.format(WARNING_INVALID_MEDIA_TYPE, mediaType));
        }
        return true;
    }

    @Override
    public OaiPmhResponse<T> readFrom(final Class<OaiPmhResponse<T>> clazz, final Type type,
            final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> headers,
            final InputStream body) throws IOException, WebApplicationException {

        try {
            return unmarshaller.unmarshal(builder.parse(body).getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException e) { // TODO
            throw new RuntimeException(e);
        }
    }
}
