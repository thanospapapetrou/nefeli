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
			throw new IllegalArgumentException(String.format("Metadata prefix must adhere to pattern %1$s", PATTERN));
		}
		this.metadataPrefix = metadataPrefix;
	}

	@SuppressWarnings("unused")
	private MetadataPrefix() {
		metadataPrefix = null;
	}
	
	@Override
	public boolean equals(final Object object) {
		return (object instanceof MetadataPrefix) && metadataPrefix.equals(((MetadataPrefix) object).metadataPrefix);
	}
	
	@Override
	public int hashCode() {
		return metadataPrefix.hashCode();
	}

	@Override
	public String toString() {
		return metadataPrefix;
	}
}
