package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing the <code>error</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OAI-PMHerrorType", propOrder = {"value"})
public class Error {
	@XmlAttribute(name = "code", required = true)
	@XmlSchemaType(name = "OAI-PMHerrorcodeType", namespace = OaiPmh.NAMESPACE)
	private final Code code;

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String value;

	/**
	 * Construct a new <code>error</code> element.
	 * @param code the <code>code</code> attribute
	 * @param value the value
	 */
	public Error(final Code code, final String value) {
		this.code = Objects.requireNonNull(code, "Code must not be null");
		this.value = Objects.requireNonNull(value, "Value must not be null");
	}

	@SuppressWarnings("unused")
	private Error() {
		code = null;
		value = null;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link Code }
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getValue() {
		return value;
	}
}
