package io.github.thanospapapetrou.nefeli;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

@ApplicationScoped
public class Configuration {
    private static final String SCHEMA_PROTOCOLS = "http,https";

    @Produces
    public SchemaFactory getSchemaFactory(final ErrorHandler handler) throws SAXException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, SCHEMA_PROTOCOLS);
        factory.setErrorHandler(handler);
        return factory;
    }

    @ApplicationScoped
    @Produces
    public Schema getSchema(final SchemaFactory factory) throws SAXException {
        return factory.newSchema();
    }

    @Produces
    public DocumentBuilderFactory getDocumentBuilderFactory(final Schema schema) throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setXIncludeAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, SCHEMA_PROTOCOLS);
        factory.setSchema(schema);
        return factory;
    }

    @Produces
    public DocumentBuilder getDocumentBuilder(final DocumentBuilderFactory factory, final EntityResolver resolver,
            final ErrorHandler handler) throws ParserConfigurationException {
        final DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(resolver);
        builder.setErrorHandler(handler);
        return builder;
    }
}
