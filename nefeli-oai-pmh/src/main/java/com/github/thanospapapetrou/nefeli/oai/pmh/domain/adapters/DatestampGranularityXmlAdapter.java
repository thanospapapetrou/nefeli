package com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;

/**
 * XML adapter that marshals/unmarshals datestamps to/from strings using the granularity specified.
 * 
 * @author thanos
 */
public class DatestampGranularityXmlAdapter extends XmlAdapter<String, Date> {
	private static final TimeZone UTC = TimeZone.getTimeZone("GMT+00:00");
	private static final Map<Granularity, String> PATTERNS = new HashMap<Granularity, String>() {
		private static final long serialVersionUID = 0L;

		{
			put(Granularity.DAY, "yyyy-MM-dd");
			put(Granularity.SECONDS, "yyyy-MM-dd'T'HH:mm:ss'Z'");

		}
	};

	private final SimpleDateFormat dateFormat;

	/**
	 * Construct a new datestamp granularity XML adapter.
	 * 
	 * @param granularity
	 *            the granularity to use when marshaling/unmarshaling datestamps
	 */
	public DatestampGranularityXmlAdapter(final Granularity granularity) {
		dateFormat = new SimpleDateFormat(PATTERNS.get(Objects.requireNonNull(granularity, "Granularity must not be null")));
		dateFormat.setLenient(false);
	}

	@Override
	public Date unmarshal(final String string) throws ParseException {
		if (string == null) {
			return null;
		}
		dateFormat.setTimeZone(UTC);
		final ParsePosition parsePosition = new ParsePosition(0);
		final Date date = dateFormat.parse(string, parsePosition);
		if (date == null) {
			throw new ParseException(String.format("String must adhere to pattern %1$s", dateFormat.toPattern()), parsePosition.getErrorIndex());
		}
		if (parsePosition.getIndex() != string.length()) {
			throw new ParseException(String.format("String must adhere to pattern %1$s", dateFormat.toPattern()), parsePosition.getIndex());
		}
		return date;
	}

	@Override
	public String marshal(final Date date) {
		dateFormat.setTimeZone(UTC);
		return (date == null) ? null : dateFormat.format(date);
	}
}
