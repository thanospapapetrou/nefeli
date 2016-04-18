package com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;

public class DateGranularityXmlAdapter extends XmlAdapter<String, Date> {
	private static final TimeZone UTC = TimeZone.getTimeZone("GMT+00:00");
	private static final Map<Granularity, String> PATTERNS = new HashMap<Granularity, String>() {
		private static final long serialVersionUID = 0L;

		{
			put(Granularity.DAY, "yyyy-MM-dd");
			put(Granularity.SECONDS, "yyyy-MM-dd'T'HH:mm:ss'Z'");

		}
	};

	private final DateFormat dateFormat;

	public DateGranularityXmlAdapter(final Granularity granularity) {
		dateFormat = new SimpleDateFormat(PATTERNS.get(Objects.requireNonNull(granularity, "Granularity must not be null")));
	}

	@Override
	public Date unmarshal(final String string) throws ParseException {
		dateFormat.setTimeZone(UTC);
		return (string == null) ? null : dateFormat.parse(string);
	}

	@Override
	public String marshal(final Date date) {
		dateFormat.setTimeZone(UTC);
		return (date == null) ? null : dateFormat.format(date);
	}
}
