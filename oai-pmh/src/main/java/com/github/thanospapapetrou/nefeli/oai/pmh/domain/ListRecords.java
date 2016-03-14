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
 * Class representing a <code>ListRecords</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRecordsType", propOrder = {"records", "resumptionToken"})
public class ListRecords {
	@XmlElement(name = "record", required = true)
	@XmlSchemaType(name = "recordType", namespace = OaiPmh.NAMESPACE)
	private final List<Record> records;

	@XmlElement(name = "resumptionToken")
	@XmlSchemaType(name = "resumptionTokenType", namespace = OaiPmh.NAMESPACE)
	private final ResumptionToken resumptionToken;

	/**
	 * Construct a new <code>ListRecords</code> element.
	 * 
	 * @param records
	 *            the <code>record</code> elements
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> element or <code>null</code> to leave it unspecified
	 */
	public ListRecords(final List<Record> records, final ResumptionToken resumptionToken) {
		this.records = new ArrayList<Record>(Objects.requireNonNull(records, "Records must not be null"));
		this.records.removeAll(Collections.singleton(null));
		if (this.records.isEmpty()) {
			throw new IllegalArgumentException("Records must contain at least one non null element");
		}
		this.resumptionToken = resumptionToken;
	}

	/**
	 * Construct a new <code>ListRecords</code> element with the <code>resumptionToken</code> element left unspecified.
	 * 
	 * @param records
	 *            the <code>record</code> elements
	 */
	public ListRecords(final List<Record> records) {
		this(records, null);
	}

	@SuppressWarnings("unused")
	private ListRecords() {
		records = new ArrayList<Record>();
		resumptionToken = null;
	}

	/**
	 * Get the <code>record</code> elements.
	 * 
	 * @return the <code>record</code> elements
	 */
	public List<Record> getRecords() {
		return Collections.unmodifiableList(records);
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
