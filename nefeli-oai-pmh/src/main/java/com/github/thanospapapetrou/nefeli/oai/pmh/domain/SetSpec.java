package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing a <code>setSpec</code> OAI-PMH element or the <code>set</code> attribute of a <code>request</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SetSpec.TYPE, propOrder = {"setSpec"})
public class SetSpec {
	static final String TYPE = "setSpecType";
	private static final Pattern PATTERN = Pattern.compile("([A-Za-z0-9\\-_\\.!~\\*'\\(\\)])+(:[A-Za-z0-9\\-_\\.!~\\*'\\(\\)]+)*");
	private static final char SEPARATOR = ':';

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String setSpec;

	/**
	 * Construct a new <code>setSpec</code> element or <code>set</code> attribute for a <code>request</code> element.
	 * 
	 * @param setSpec
	 *            the setSpec
	 */
	public SetSpec(final String setSpec) {
		Objects.requireNonNull(setSpec, "Set spec must not be null");
		if (!PATTERN.matcher(setSpec).matches()) {
			throw new IllegalArgumentException(String.format("Set spec must adhere to pattern %1$s", PATTERN));
		}
		this.setSpec = setSpec;
	}
	
	@SuppressWarnings("unused")
	private SetSpec() {
		setSpec = null;
	}

	/**
	 * Get the hierarchical parent of this <code>setSpec</code>.
	 * 
	 * @return the hierarchical parent of this <code>setSpec</code> or <code>null</code> if this <code>setSpec</code> has no parent
	 */
	public SetSpec getParent() {
		final int index = setSpec.lastIndexOf(SEPARATOR);
		return (index == -1) ? null : new SetSpec(setSpec.substring(0, index));
	}
	
	@Override
	public boolean equals(final Object object) {
		return (object instanceof SetSpec) && setSpec.equals(((SetSpec) object).setSpec);
	}
	
	@Override
	public int hashCode() {
		return setSpec.hashCode();
	}

	@Override
	public String toString() {
		return setSpec;
	}
}
