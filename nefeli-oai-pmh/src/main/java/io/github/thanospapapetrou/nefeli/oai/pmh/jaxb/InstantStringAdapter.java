package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2.Granularity;

public class InstantStringAdapter extends XmlAdapter<String, Instant> {
    private final Granularity granularity;

    public InstantStringAdapter(final Granularity granularity) {
        this.granularity = granularity;
    }

    @Override
    public String marshal(final Instant instant) {
        return granularity.getFormatter().format(instant);
    }

    @Override
    public Instant unmarshal(final String string) {
        if (granularity == null) {
            try {
                return unmarshal(string, Granularity.YYYY_MM_DD_THH_MM_SS_Z);
            } catch (final DateTimeParseException e) {
                return unmarshal(string, Granularity.YYYY_MM_DD);
            }
        }
        return unmarshal(string, granularity);
    }

    private Instant unmarshal(final String string, final Granularity granularity) throws DateTimeParseException {
        return (string == null) ? null : LocalDateTime.parse(string, granularity.getFormatter()).toInstant(ZoneOffset.UTC);
    }

}
