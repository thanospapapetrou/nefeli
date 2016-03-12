package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing the <code>metadataPrefix</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataPrefixType")
public class MetadataPrefix {
	private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9\\-_\\.!~\\*'\\(\\)]+");

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String value;

	/**
	 * Construct a new <code>metadataPrefix</code> element.
	 * 
	 * @param value
	 *            the value
	 */
	public MetadataPrefix(final String value) {
		Objects.requireNonNull(value, "Value must not be null");
		if (!PATTERN.matcher(value).matches()) {
			throw new IllegalArgumentException("Value must adhere to pattern " + PATTERN);
		}
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
