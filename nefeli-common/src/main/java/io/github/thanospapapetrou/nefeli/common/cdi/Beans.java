package io.github.thanospapapetrou.nefeli.common.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

@ApplicationScoped
public class Beans {
    private static final String PROTOCOLS = "http,https";

    @Produces
    public SchemaFactory getFactory(final LSResourceResolver resolver, final ErrorHandler handler) throws SAXException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, PROTOCOLS);
        factory.setResourceResolver(resolver);
        factory.setErrorHandler(handler);
        return factory;
    }
}
