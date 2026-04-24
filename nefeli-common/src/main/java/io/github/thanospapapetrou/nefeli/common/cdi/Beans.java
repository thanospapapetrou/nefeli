package io.github.thanospapapetrou.nefeli.common.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEventHandler;
import jakarta.xml.bind.annotation.XmlSchema;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

@ApplicationScoped
public class Beans {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    public @interface Jaxb {
        class Literal extends AnnotationLiteral<Jaxb> implements Jaxb {
            private final Class<?> value;

            public Literal(final Class<?> value) {
                this.value = value;
            }

            public Class<?> value() {
                return value;
            }
        }

        @Nonbinding Class<?> value() default Object.class;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    public @interface Xsd {
        class Literal extends AnnotationLiteral<Xsd> implements Xsd {
            private final String value;

            public Literal(final String value) {
                this.value = value;
            }

            public String value() {
                return value;
            }
        }

        @Nonbinding String value() default "";
    }

    private static final String DELIMITER = " ";
    private static final String PROTOCOLS = "http,https";

    @Jaxb
    @Produces
    public Marshaller getMarshaller(final Instance<JAXBContext> context, final Instance<Schema> schema,
            final ValidationEventHandler handler, final InjectionPoint point) throws JAXBException {
        final Jaxb jaxb = point.getAnnotated().getAnnotation(Jaxb.class);
        final Marshaller marshaller = context.select(jaxb).get().createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        final XmlSchema xmlSchema = jaxb.value().getPackage().getAnnotation(XmlSchema.class);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
                xmlSchema.namespace() + DELIMITER + xmlSchema.location());
        marshaller.setSchema(schema.select(new Xsd.Literal(xmlSchema.location())).get());
        marshaller.setEventHandler(handler);
        return marshaller;
    }

    @Jaxb
    @Produces
    public Unmarshaller getUnmarshaller(final Instance<JAXBContext> context, final Instance<Schema> schema,
            final ValidationEventHandler handler, final InjectionPoint point) throws JAXBException {
        final Jaxb jaxb = point.getAnnotated().getAnnotation(Jaxb.class);
        final Unmarshaller unmarshaller = context.select(jaxb).get().createUnmarshaller();
        unmarshaller.setSchema(schema.select(new Xsd.Literal(jaxb.value().getPackage().getAnnotation(XmlSchema.class)
                .location())).get());
        unmarshaller.setEventHandler(handler);
        return unmarshaller;
    }

    @ApplicationScoped
    @Jaxb
    @Produces
    private JAXBContext getContext(final InjectionPoint point) throws JAXBException {
        return JAXBContext.newInstance(point.getAnnotated().getAnnotation(Jaxb.class).value());
    }

    @Produces
    @Xsd
    public DocumentBuilder getBuilder(final Instance<DocumentBuilderFactory> factory, final EntityResolver resolver,
            final ErrorHandler handler, final InjectionPoint point) throws ParserConfigurationException {
        final DocumentBuilder builder = factory.select(point.getAnnotated().getAnnotation(Xsd.class)).get()
                .newDocumentBuilder();
        builder.setEntityResolver(resolver);
        builder.setErrorHandler(handler);
        return builder;
    }

    @Produces
    @Xsd
    public DocumentBuilderFactory getFactory(final Instance<Schema> schema, final InjectionPoint point)
            throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setXIncludeAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, PROTOCOLS);
        factory.setSchema(schema.select(point.getAnnotated().getAnnotation(Xsd.class)).get());
        return factory;
    }

    @ApplicationScoped
    @Produces
    @Xsd
    public Schema getSchema(final SchemaFactory factory, final InjectionPoint point)
            throws MalformedURLException, SAXException, URISyntaxException {
        return factory.newSchema(new URI(point.getAnnotated().getAnnotation(Xsd.class).value()).toURL());
    }

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
