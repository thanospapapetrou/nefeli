package org.openarchives.oai._2;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.BiFunction;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * &lt;p&gt;Java class for granularityType&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * &lt;pre&gt;{&#064;code
 * &lt;simpleType name="granularityType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="YYYY-MM-DD"/&gt;
 *     &lt;enumeration value="YYYY-MM-DDThh:mm:ssZ"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * }&lt;/pre&gt;
 * 
 */
@XmlType(name = "granularityType")
@XmlEnum
public enum Granularity {
    @XmlEnumValue("YYYY-MM-DD")
    DAY(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT),
            (instant, formatter) -> formatter.format(instant.atZone(ZoneOffset.UTC).toLocalDate()),
            (string, formatter) -> LocalDate.parse(string, formatter).atStartOfDay().toInstant(ZoneOffset.UTC)),
    @XmlEnumValue("YYYY-MM-DDThh:mm:ssZ")
    SECONDS(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT),
            (instant, formatter) -> formatter.format(instant.atZone(ZoneOffset.UTC).toLocalDateTime()),
            (string, formatter) -> LocalDateTime.parse(string, formatter).toInstant(ZoneOffset.UTC));

    private final DateTimeFormatter format;
    private final BiFunction<Instant, DateTimeFormatter, String> formatter;
    private final BiFunction<String, DateTimeFormatter, Instant> parser;

    Granularity(final DateTimeFormatter format, final BiFunction<Instant, DateTimeFormatter, String> formatter,
            final BiFunction<String, DateTimeFormatter, Instant> parser) {
        this.format = format;
        this.formatter = formatter;
        this.parser = parser;
    }

    public String format(final Instant instant) {
        return (instant == null) ? null : formatter.apply(instant, format);
    }

    public Instant parse(final String string) {
        return (string == null) ? null : parser.apply(string, format);
    }

    @Override
    public String toString() {
        try {
            return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
        } catch (final NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
}
