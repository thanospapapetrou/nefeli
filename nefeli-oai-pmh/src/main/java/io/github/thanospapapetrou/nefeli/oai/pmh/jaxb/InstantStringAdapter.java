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
        return granularity.format(instant);
    }

    @Override
    public Instant unmarshal(final String string) {
        if (granularity == null) {
            try {
                return Granularity.SECONDS.parse(string);
            } catch (final DateTimeParseException e) {
                return Granularity.DAY.parse(string);
            }
        }
        return granularity.parse(string);
    }
}
