package io.github.thanospapapetrou.nefeli.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

@ApplicationScoped
public class Configuration {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    public @interface Property {
        @Nonbinding String value() default "";
    }

    private static final String DELIMITER = ",";
    private static final String PROPERTIES = "/nefeli.properties";

    private final Properties properties;

    public Configuration() throws IOException {
        this(new Properties());
        try (final InputStream properties = getClass().getResourceAsStream(PROPERTIES)) { // TODO load properties
            // from system
            this.properties.load(properties);
        }
    }

    private Configuration(final Properties properties) {
        this.properties = properties;
    }

    @ApplicationScoped
    @Produces
    public Clock getClock() {
        return Clock.systemUTC();
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

    @Produces
    @Property
    public List<String> getStrings(final InjectionPoint point) {
        return (getString(point) == null) ? List.of() : Arrays.asList(getString(point).split(DELIMITER));
    }

    @Produces
    @Property
    public List<InternetAddress> getInternetAddresses(final InjectionPoint point) throws AddressException {
        final List<InternetAddress> internetAddresses = new ArrayList<>();
        for (final String string : getStrings(point)) {
            internetAddresses.add(new InternetAddress(string));
        }
        return internetAddresses;
    }

    public <E extends Enum<E>> E getEnumeration(final InjectionPoint point) {
        return (E) Enum.valueOf(Class.class.cast(point.getType()), getString(point));
    }
}
