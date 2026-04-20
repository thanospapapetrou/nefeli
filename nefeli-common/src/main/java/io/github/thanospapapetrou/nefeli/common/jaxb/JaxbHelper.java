package io.github.thanospapapetrou.nefeli.common.jaxb;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEventHandler;
import jakarta.xml.bind.annotation.XmlSchema;

import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import io.github.thanospapapetrou.nefeli.common.schema.SchemaHelper;

@ApplicationScoped
public class JaxbHelper {
    private static final String DELIMITER = " ";

    private final SchemaHelper schema;
    private final ValidationEventHandler handler;
    private final Map<Class<?>, JAXBContext> contexts;

    @Inject
    public JaxbHelper(final SchemaHelper schema, final ValidationEventHandler handler) {
        this(schema, handler, new HashMap<>());
    }

    private JaxbHelper(final SchemaHelper schema, final ValidationEventHandler handler,
            final Map<Class<?>, JAXBContext> contexts) {
        this.schema = schema;
        this.handler = handler;
        this.contexts = contexts;
    }

    public Schema getSchema(final Class<?> clazz) throws MalformedURLException, SAXException, URISyntaxException {
        return schema.getSchema(getSchemaUrl(clazz));
    }

    public Marshaller getMarshaller(final Class<?> clazz)
            throws JAXBException, MalformedURLException, SAXException, URISyntaxException {
        final Marshaller marshaller = getContext(clazz).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, getNamespace(clazz) + DELIMITER + getSchemaUrl(clazz));
        marshaller.setSchema(getSchema(clazz));
        marshaller.setEventHandler(handler);
        return marshaller;
    }

    public Unmarshaller getUnmarshaller(final Class<?> clazz)
            throws JAXBException, MalformedURLException, SAXException, URISyntaxException {
        final Unmarshaller unmarshaller = getContext(clazz).createUnmarshaller();
        unmarshaller.setSchema(getSchema(clazz));
        unmarshaller.setEventHandler(handler);
        return unmarshaller;
    }

    private JAXBContext getContext(final Class<?> clazz) throws JAXBException {
        synchronized (contexts) {
            if (!contexts.containsKey(clazz)) {
                contexts.put(clazz, JAXBContext.newInstance(clazz));
            }
            return contexts.get(clazz);
        }
    }

    private URL getSchemaUrl(final Class<?> clazz) throws MalformedURLException, URISyntaxException {
        return new URI(clazz.getPackage().getAnnotation(XmlSchema.class).location()).toURL();
    }

    private URI getNamespace(final Class<?> clazz) throws URISyntaxException {
        return new URI(clazz.getPackage().getAnnotation(XmlSchema.class).namespace());
    }
}
