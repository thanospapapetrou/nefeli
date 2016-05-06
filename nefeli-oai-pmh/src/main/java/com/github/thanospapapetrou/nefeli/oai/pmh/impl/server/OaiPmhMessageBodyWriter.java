package com.github.thanospapapetrou.nefeli.oai.pmh.impl.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmhResponse;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampGranularityXmlAdapter;

/**
 * Message body writer for <code>OAI-PMH</code> elements.
 * 
 * @author thanos
 */
@Produces(MediaType.TEXT_XML)
@Provider
public class OaiPmhMessageBodyWriter implements MessageBodyWriter<OaiPmhResponse> {
	private static final JAXBContext CONTEXT;
	private static final Schema SCHEMA;
	private static final String SCHEMA_LOCATION = "%1$s %2$s";

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
	 * Construct a new OAI-PMH message body writer.
	 * 
	 * @param granularity
	 *            the granularity to use for writing
	 */
	public OaiPmhMessageBodyWriter(final Granularity granularity) {
		this.granularity = Objects.requireNonNull(granularity, "Granularity must not be null");
	}

	@Override
	public long getSize(final OaiPmhResponse oaiPmh, final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType) {
		return -1L;
	}

	@Override
	public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType) {
		return OaiPmhResponse.class.equals(clazz) && OaiPmhResponse.class.equals(type) && MediaType.TEXT_XML_TYPE.isCompatible(mediaType);
	}

	@Override
	public void writeTo(final OaiPmhResponse oaiPmh, final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> headers, final OutputStream output) throws IOException {
		headers.putSingle(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_TYPE.withCharset(StandardCharsets.UTF_8.name()));
		headers.putSingle(HttpHeaders.DATE, oaiPmh.getResponseDate());
		try {
			final Marshaller marshaller = CONTEXT.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, String.format(SCHEMA_LOCATION, OaiPmhResponse.class.getPackage().getAnnotation(XmlSchema.class).namespace(), OaiPmhResponse.class.getPackage().getAnnotation(XmlSchema.class).location()));
			marshaller.setSchema(SCHEMA);
			// marshaller.setEventHandler(handler); // TODO
			marshaller.setAdapter(DatestampGranularityXmlAdapter.class, new DatestampGranularityXmlAdapter(granularity));
			marshaller.marshal(oaiPmh, output);
		} catch (final JAXBException e) {
			throw new IOException("Error writing OAI-PMH", e);
		}
	}
}
