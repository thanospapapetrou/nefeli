package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing a <code>metadataPrefix</code> OAI-PMH element or the <code>metadataPrefix</code> attribute of a <code>request</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = MetadataPrefix.TYPE)
public class MetadataPrefix {
	static final String TYPE = "metadataPrefixType";
	private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9\\-_\\.!~\\*'\\(\\)]+");

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String metadataPrefix;

	/**
	 * Construct a new <code>metadataPrefix</code> element.
	 * 
	 * @param metadataPrefix
	 *            the metadataPrefix
	 */
	public MetadataPrefix(final String metadataPrefix) {
		Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null");
		if (!PATTERN.matcher(metadataPrefix).matches()) {
			throw new IllegalArgumentException("Metadata prefix must adhere to pattern " + PATTERN);
		}
		this.metadataPrefix = metadataPrefix;
	}

	@Override
	public String toString() {
		return metadataPrefix;
	}
}
