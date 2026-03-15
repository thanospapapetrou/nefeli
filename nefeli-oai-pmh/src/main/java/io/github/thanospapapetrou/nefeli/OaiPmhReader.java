package io.github.thanospapapetrou.nefeli;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.OaiPmhResponse;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.jaxb.adapters.InstantStringAdapter;

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
            final ErrorHandler errorHandler = new NefeliErrorHandler();
            final DocumentBuilder builder =
                    getDocumentBuilder(getDocumentBuilderFactory(getSchema(getSchemaFactory(errorHandler))),
                            errorHandler);
            final Document document = builder.parse(body);
            final Unmarshaller unmarshaller = JAXBContext.newInstance(OaiPmhResponse.class).createUnmarshaller();
            unmarshaller.setAdapter(InstantStringAdapter.class,
                    new InstantStringAdapter(Granularity.YYYY_MM_DD_THH_MM_SS_Z)); // TODO switch granularity
            return unmarshaller.unmarshal(document.getDocumentElement(), OaiPmhResponse.class).getValue();
        } catch (final JAXBException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private DocumentBuilderFactory getDocumentBuilderFactory(final Schema schema) throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setXIncludeAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "http,https");
        factory.setSchema(schema);
        return factory;
    }

    private DocumentBuilder getDocumentBuilder(final DocumentBuilderFactory factory, final ErrorHandler handler)
            throws ParserConfigurationException {
        final DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(final String publicId, final String systemId)
                    throws SAXException, IOException {
                final HttpURLConnection connection = (HttpURLConnection) new URL(systemId).openConnection();
                HttpsURLConnection.setFollowRedirects(true);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod(HttpMethod.GET);
                connection.connect();
                if (Response.Status.fromStatusCode(connection.getResponseCode()).getFamily()
                        == Response.Status.Family.REDIRECTION) {
                    return resolveEntity(publicId, connection.getHeaderField(HttpHeaders.LOCATION));
                }
                final InputSource source = new InputSource(systemId);
                source.setByteStream(connection.getInputStream());
                return source;
            }
        });
        builder.setErrorHandler(handler);
        return builder;
    }

    private SchemaFactory getSchemaFactory(final ErrorHandler handler) throws SAXException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "http,https");
        factory.setErrorHandler(handler);
        return factory;
    }

    private Schema getSchema(final SchemaFactory factory) throws SAXException {
        return factory.newSchema();
    }
}
