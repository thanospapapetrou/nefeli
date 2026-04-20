package io.github.thanospapapetrou.nefeli.common.xml;

import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

@ApplicationScoped
public class XmlHelper {
    private static final String PROTOCOLS = "http,https";

    private final EntityResolver resolver;
    private final ErrorHandler handler;
    private final Map<Schema, DocumentBuilderFactory> factories;

    @Inject
    public XmlHelper(final EntityResolver resolver, final ErrorHandler handler) {
        this(resolver, handler, new HashMap<>());
    }

    private XmlHelper(final EntityResolver resolver, final ErrorHandler handler,
            final Map<Schema, DocumentBuilderFactory> factories) {
        this.resolver = resolver;
        this.handler = handler;
        this.factories = factories;
    }

    public DocumentBuilder getBuilder(final Schema schema) throws ParserConfigurationException {
        final DocumentBuilder builder = getFactory(schema).newDocumentBuilder();
        builder.setEntityResolver(resolver);
        builder.setErrorHandler(handler);
        return builder;
    }

    private DocumentBuilderFactory getFactory(final Schema schema) throws ParserConfigurationException {
        synchronized (factories) {
            if (!factories.containsKey(schema)) {
                factories.put(schema, DocumentBuilderFactory.newInstance());
                factories.get(schema).setCoalescing(false);
                factories.get(schema).setExpandEntityReferences(true);
                factories.get(schema).setIgnoringComments(true);
                factories.get(schema).setIgnoringElementContentWhitespace(false);
                factories.get(schema).setNamespaceAware(true);
                factories.get(schema).setValidating(false);
                factories.get(schema).setXIncludeAware(true);
                factories.get(schema).setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                factories.get(schema).setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, PROTOCOLS);
                factories.get(schema).setSchema(schema);
            }
            return factories.get(schema);
        }
    }
}
