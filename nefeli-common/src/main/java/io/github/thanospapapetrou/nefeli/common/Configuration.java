package io.github.thanospapapetrou.nefeli.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;

@ApplicationScoped
public class Configuration {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    public @interface Property {
        @Nonbinding String value() default "";
    }

    private static final String PROPERTIES = "/nefeli.properties";

    private final Properties properties;

    public Configuration() throws IOException {
        this(new Properties());
        try (final InputStream properties = getClass().getResourceAsStream(PROPERTIES)) {
            this.properties.load(properties);
        }
    }

    private Configuration(final Properties properties) {
        this.properties = properties;
    }

    @Produces
    @Property
    public String getString(final InjectionPoint point) {
        return properties.getProperty(point.getAnnotated().getAnnotation(Property.class).value());
    }

    @Produces
    @Property
    public int getInt(final InjectionPoint point) {
        return Integer.parseInt(getString(point));
    }

    @Produces
    @Property
    public Duration getDuration(final InjectionPoint point) {
        return Duration.parse(getString(point));
    }
}
