package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmhResponse;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampGranularityXmlAdapter;

/**
 * Message body reader for <code>OAI-PMH</code> elements.
 * 
 * @author thanos
 */
@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
@Provider
public class OaiPmhMessageBodyReader implements MessageBodyReader<OaiPmhResponse> {
	private static final JAXBContext CONTEXT;
	private static final Schema SCHEMA;
	private static final Logger LOGGER = Logger.getLogger(OaiPmhMessageBodyReader.class.getName());

	private final Granularity granularity;

	static {
		try {
			CONTEXT = JAXBContext.newInstance(OaiPmhResponse.class);
			SCHEMA = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema();
		} catch (final JAXBException | SAXException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Construct a new OAI-PMH message body reader.
	 * 
	 * @param granularity
	 *            the granularity to use for reading
	 */
	public OaiPmhMessageBodyReader(final Granularity granularity) {
		this.granularity = Objects.requireNonNull(granularity, "Granularity must not be null");
	}

	@Override
	public boolean isReadable(final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType) {
		return clazz.equals(OaiPmhResponse.class) && type.equals(OaiPmhResponse.class) && (mediaType.isCompatible(MediaType.TEXT_XML_TYPE) || mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE));
	}

	@Override
	public OaiPmhResponse readFrom(final Class<OaiPmhResponse> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> headers, final InputStream input) throws IOException {
		if (!mediaType.isCompatible(MediaType.TEXT_XML_TYPE)) {
			LOGGER.warning(String.format("Response media type %1$s is not compatible with %2$s", mediaType.toString(), MediaType.TEXT_XML_TYPE.toString()));
		}
		final Charset charset = mediaType.getParameters().containsKey(MediaType.CHARSET_PARAMETER) ? Charset.forName(mediaType.getParameters().get(MediaType.CHARSET_PARAMETER)) : StandardCharsets.ISO_8859_1;
		if (!charset.equals(StandardCharsets.UTF_8)) {
			LOGGER.warning(String.format("Response charset %1$s is not %2$s", charset.name(), StandardCharsets.UTF_8.name()));
		}
		try {
			final Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			unmarshaller.setSchema(SCHEMA);
			// unmarshaller.setEventHandler(handler); // TODO
			unmarshaller.setAdapter(DatestampGranularityXmlAdapter.class, new DatestampGranularityXmlAdapter(granularity));
			return (OaiPmhResponse) unmarshaller.unmarshal(new InputStreamReader(input, charset));
		} catch (final JAXBException e) {
			throw new IOException("Error reading OAI-PMH", e);
		}
	}
}
