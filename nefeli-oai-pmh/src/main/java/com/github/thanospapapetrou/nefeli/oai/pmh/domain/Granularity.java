package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining a <code>granularity</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = Granularity.TYPE)
public enum Granularity {
	/**
	 * day granularity (<code>YYYY-MM-DD</code>)
	 */
	@XmlEnumValue("YYYY-MM-DD")
	YYYY_MM_DD("day", "yyyy-MM-dd"),

	/**
	 * seconds granularity (<code>YYYY-MM-DDThh:mm:ssZ</code>)
	 */
	@XmlEnumValue("YYYY-MM-DDThh:mm:ssZ")
	YYYY_MM_DD_THH_MM_SS_Z("seconds", "yyyy-MM-dd'T'HH:mm:ss'Z'");

	static final String TYPE = "granularityType";
	private static final TimeZone UTC = TimeZone.getTimeZone("GMT+00:00");

	private final String string;
	private final SimpleDateFormat dateFormat;

	private Granularity(final String string, final String dateFormat) {
		this.string = string;
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}

	public String format(final Date date) {
		dateFormat.setTimeZone(UTC);
		return dateFormat.format(date);
	}

	@Override
	public String toString() {
		return string;
	}
}
