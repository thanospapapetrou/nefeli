package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class representing a <code>ListIdentifiers</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListIdentifiersType", propOrder = {"headers", "resumptionToken"})
public class ListIdentifiers {
	@XmlElement(name = "header", required = true)
	@XmlSchemaType(name = "headerType", namespace = OaiPmh.NAMESPACE)
	private final List<Header> headers;

	@XmlElement(name = "resumptionToken")
	@XmlSchemaType(name = "resumptionTokenType", namespace = OaiPmh.NAMESPACE)
	private final ResumptionToken resumptionToken;

	/**
	 * Construct a new <code>ListIdentifiers</code> element.
	 * 
	 * @param headers
	 *            the <code>header</code> elements
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> element or <code>null</code> to leave it unspecified
	 */
	public ListIdentifiers(final List<Header> headers, final ResumptionToken resumptionToken) {
		this.headers = new ArrayList<Header>(Objects.requireNonNull(headers, "Headers must not be null"));
		this.headers.removeAll(Collections.singleton(null));
		if (this.headers.isEmpty()) {
			throw new IllegalArgumentException("Headers must contain at least one non null element");
		}
		this.resumptionToken = resumptionToken;
	}

	/**
	 * Construct a new <code>ListIdentifiers</code> element with the <code>resumptionToken</code> element left unspecified.
	 * 
	 * @param headers
	 *            the <code>header</code> elements
	 */
	public ListIdentifiers(final List<Header> headers) {
		this(headers, null);
	}

	@SuppressWarnings("unused")
	private ListIdentifiers() {
		headers = new ArrayList<Header>();
		resumptionToken = null;
	}

	/**
	 * Get the <code>header</code> elements.
	 * 
	 * @return the <code>header</code> elements
	 */
	public List<Header> getHeaders() {
		return Collections.unmodifiableList(headers);
	}

	/**
	 * Get the <code>resumptionToken</code> element.
	 * 
	 * @return the <code>resumptionToken</code> element or <code>null</code> if unspecified
	 */
	public ResumptionToken getResumptionToken() {
		return resumptionToken;
	}
}
