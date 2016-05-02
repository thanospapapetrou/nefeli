package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class representing a <code>GetRecord</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = GetRecord.TYPE, propOrder = {"record"})
public class GetRecord {
	static final String TYPE = "GetRecordType";

	@XmlElement(name = "record", required = true)
	@XmlSchemaType(name = Record.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final Record record;

	/**
	 * Construct a new <code>GetRecord</code> element.
	 * 
	 * @param record
	 *            the <code>record</code> element
	 */
	public GetRecord(final Record record) {
		this.record = Objects.requireNonNull(record, "Record must not be null");
	}

	@SuppressWarnings("unused")
	private GetRecord() {
		record = null;
	}

	/**
	 * Get the <code>record</code> element.
	 * 
	 * @return the <code>record</code> element
	 */
	public Record getRecord() {
		return record;
	}

	@Override
	public boolean equals(final Object object) {
		return (object instanceof GetRecord) && record.equals(((GetRecord) object).record);
	}

	@Override
	public int hashCode() {
		return record.hashCode();
	}
}
