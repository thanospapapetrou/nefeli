package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
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

import javax.xml.transform.stream.StreamSource;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhBody;
import org.openarchives.oai._2.OaiPmhResponse;

import io.github.thanospapapetrou.nefeli.common.cdi.Beans;
import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;

@Consumes({OaiPmhResponse.CONTENT_TYPE, MediaType.WILDCARD})
@Provider
public class OaiPmhReader<T extends OaiPmhBody> implements MessageBodyReader<OaiPmhResponse<T>> {
    private static final String ERROR_READING = "Error reading OAI-PMH response";
    private static final Logger LOGGER = Logger.getLogger(OaiPmhReader.class.getName());
    private static final String WARNING_INVALID_MEDIA_TYPE = "Invalid  media type %1$s";

    private final Unmarshaller unmarshaller;

    @Inject
    public OaiPmhReader(@Beans.Jaxb(OaiPmhResponse.class) final Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
        setGranularity(null);
    }

    public void setGranularity(final Granularity granularity) {
        unmarshaller.setAdapter(InstantStringAdapter.class, new InstantStringAdapter(granularity));
    }

    @Override
    public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations,
            final MediaType mediaType) {
        if (!mediaType.equals(MediaType.TEXT_XML_TYPE.withCharset(StandardCharsets.UTF_8.name().toLowerCase(Locale.ROOT)))) {
            LOGGER.warning(String.format(WARNING_INVALID_MEDIA_TYPE, mediaType));
        }
        return true;
    }

    @Override
    public OaiPmhResponse<T> readFrom(final Class<OaiPmhResponse<T>> clazz, final Type type,
            final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> headers,
            final InputStream body) throws IOException, WebApplicationException {
        try {
            return unmarshaller.unmarshal(new StreamSource(body), OaiPmhResponse.class).getValue();
        } catch (final JAXBException e) {
            throw new IOException(ERROR_READING, e);
        }
    }
}
