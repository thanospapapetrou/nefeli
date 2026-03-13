package io.github.thanospapapetrou.nefeli;

import java.time.Instant;
import java.util.GregorianCalendar;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class InstantAdapter extends XmlAdapter<XMLGregorianCalendar, Instant> {
    private static final DatatypeFactory FACTORY = DatatypeFactory.newDefaultInstance();

    @Override
    public XMLGregorianCalendar marshal(final Instant instant) throws Exception {
        if (instant == null) {
            return null;
        }
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(instant.toEpochMilli());
        return FACTORY.newXMLGregorianCalendar(calendar);
    }

    @Override
    public Instant unmarshal(final XMLGregorianCalendar xmlGregorianCalendar) throws Exception {
        return xmlGregorianCalendar.toGregorianCalendar().toInstant();
    }
}
