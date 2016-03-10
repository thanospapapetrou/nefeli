package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Class representing the <code>resumptionToken</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumptionTokenType", propOrder = {"value"})
public class ResumptionToken {
	@XmlAttribute(name = "expirationDate")
	@XmlSchemaType(name = "dateTime")
	// @XmlJavaTypeAdapter() TODO XMLGregorianCalendar
	private final Date expirationDate;

	@XmlAttribute(name = "completeListSize")
	@XmlSchemaType(name = "positiveInteger")
	private final BigInteger completeListSize;

	@XmlAttribute(name = "cursor")
	@XmlSchemaType(name = "nonNegativeInteger")
	private final BigInteger cursor;

	@XmlValue
	@XmlSchemaType(name = "string")
	private final String value;

	/**
	 * Construct a new <code>resumptionToken</code> element.
	 * 
	 * @param expirationDate
	 *            the <code>expirationDate</code> attribute or <code>null</code> to leave it unspecified
	 * @param completeListSize
	 *            the <code>completeListSize</code> attribute or <code>null</code> to leave it unspecified
	 * @param cursor
	 *            the <code>cursor</code> attribute or <code>null</code> to leave it unspecified
	 * @param value
	 *            the value or <code>null</code> to leave it unspecified
	 */
	public ResumptionToken(final Date expirationDate, final BigInteger completeListSize, final BigInteger cursor, final String value) {
		if ((completeListSize != null) && (completeListSize.compareTo(BigInteger.ZERO) <= 0)) {
			throw new IllegalArgumentException("Complete list size must be positive");
		}
		if ((cursor != null) && (cursor.compareTo(BigInteger.ZERO) < 0)) {
			throw new IllegalArgumentException("Cursor must be non negative");
		}
		this.expirationDate = expirationDate;
		this.completeListSize = completeListSize;
		this.cursor = cursor;
		this.value = value;
	}

	/**
	 * Construct a new <code>resumptionToken</code> element with value left unspecified.
	 * 
	 * @param expirationDate
	 *            the <code>expirationDate</code> attribute or <code>null</code> to leave it unspecified
	 * @param completeListSize
	 *            the <code>completeListSize</code> attribute or <code>null</code> to leave it unspecified
	 * @param cursor
	 *            the <code>cursor</code> attribute or <code>null</code> to leave it unspecified
	 */
	public ResumptionToken(final Date expirationDate, final BigInteger completeListSize, final BigInteger cursor) {
		this(expirationDate, completeListSize, cursor, null);
	}

	/**
	 * Construct a new <code>resumptionToken</code> element with <code>expirationDate</code>, <code>completeListSize</code> and <code>cursor</code> attributes and value left unspecified.
	 */
	public ResumptionToken() {
		this(null, null, null);
	}

	/**
	 * Get the <code>expirationDate</code> attribute.
	 * 
	 * @return the <code>expirationDate</code> attribute or <code>null</code> if unspecified
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Get the <code>completeListSize</code> attribute.
	 * 
	 * @return the <code>completeListSize</code> attribute or <code>null</code> if unspecified
	 */
	public BigInteger getCompleteListSize() {
		return completeListSize;
	}

	/**
	 * Get the <code>cursor</code> attribute.
	 * 
	 * @return the <code>cursor</code> attribute or <code>null</code> if unspecified
	 */
	public BigInteger getCursor() {
		return cursor;
	}

	/**
	 * Get the value.
	 * 
	 * @return the value or <code>null</code> if unspecified
	 */
	public String getValue() {
		return value;
	}
}
