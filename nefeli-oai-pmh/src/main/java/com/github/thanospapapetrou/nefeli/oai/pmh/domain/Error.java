package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing an <code>error</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Error.TYPE, propOrder = {"string"})
public class Error {
	static final String TYPE = "OAI-PMHerrorType";
	
	@XmlAttribute(name = "code", required = true)
	@XmlSchemaType(name = Code.TYPE, namespace = OaiPmh.NAMESPACE)
	private final Code code;

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String string;

	/**
	 * Construct a new <code>error</code> element.
	 * 
	 * @param code
	 *            the <code>code</code> attribute
	 * @param string
	 *            the string or <code>null</code> to leave it unspecified
	 */
	public Error(final Code code, final String string) {
		this.code = Objects.requireNonNull(code, "Code must not be null");
		this.string = string;
	}

	/**
	 * Construct a new <code>error</code> element with the string left unspecified.
	 * 
	 * @param code
	 *            the <code>code</code> attribute
	 */
	public Error(final Code code) {
		this(code, null);
	}

	@SuppressWarnings("unused")
	private Error() {
		code = null;
		string = null;
	}

	/**
	 * Get the <code>code</code> attribute.
	 * 
	 * @return the <code>code</code> attribute
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * Get the string.
	 * 
	 * @return the string or <code>null</code> if unspecified
	 */
	public String getString() {
		return string;
	}
}
