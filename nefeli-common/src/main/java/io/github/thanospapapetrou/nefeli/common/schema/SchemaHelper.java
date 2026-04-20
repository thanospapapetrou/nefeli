package io.github.thanospapapetrou.nefeli.common.schema;

import java.net.URL;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

@ApplicationScoped
public class SchemaHelper {
    private final SchemaFactory factory;

    @Inject
    public SchemaHelper(final SchemaFactory factory) {
        this.factory = factory;
    }

    public Schema getSchema(final URL schema) throws SAXException {
        synchronized (factory) {
            return factory.newSchema(schema);
        }
    }
}
