package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2.Granularity;

public class InstantStringAdapter extends XmlAdapter<String, Instant> {
    private final Granularity granularity;

    public InstantStringAdapter(final Granularity granularity) {
        this.granularity = granularity;
    }

    InstantStringAdapter() {
        this(null);
    }

    @Override
    public String marshal(final Instant instant) {
        return (instant == null) ? null : granularity.format(instant);
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
        return (string == null) ? null : granularity.parse(string);
    }

}
