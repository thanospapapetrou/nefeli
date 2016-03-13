package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing a <code>setSpec</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setSpecType", propOrder = {"setSpec"})
public class SetSpec {
	private static final Pattern PATTERN = Pattern.compile("([A-Za-z0-9\\-_\\.!~\\*'\\(\\)])+(:[A-Za-z0-9\\-_\\.!~\\*'\\(\\)]+)*");
	private static final char SEPARATOR = ':';

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String value;

	/**
	 * Construct a new <code>setSpec</code> element.
	 * 
	 * @param value
	 *            the value
	 */
	public SetSpec(final String value) {
		Objects.requireNonNull(value, "Value must not be null");
		if (!PATTERN.matcher(value).matches()) {
			throw new IllegalArgumentException("Value must adhere to pattern " + PATTERN);
		}
		this.value = value;
	}

	/**
	 * Get the hierarchical parent of this <code>setSpec</code>.
	 * 
	 * @return the hierarchical parent of this <code>setSpec</code> or <code>null</code> if this <code>setSpec</code> has no parent
	 */
	public SetSpec getParent() {
		final int index = value.lastIndexOf(SEPARATOR);
		return (index == -1) ? null : new SetSpec(value.substring(0, index));
	}

	@Override
	public String toString() {
		return value;
	}
}
