package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>verb</code> OAI-PMH attribute.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "verbType")
public enum Verb {
	/**
	 * <code>Identify</code>
	 */
	@XmlEnumValue("Identify")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	IDENTIFY,

	/**
	 * <code>ListMetadataFormats</code>
	 */
	@XmlEnumValue("ListMetadataFormats")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	LIST_METADATA_FORMATS,

	/**
	 * <code>ListSets</code>
	 */
	@XmlEnumValue("ListSets")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	LIST_SETS,

	/**
	 * <code>GetRecord</code>
	 */
	@XmlEnumValue("GetRecord")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	GET_RECORD,

	/**
	 * <code>ListIdentifiers</code>
	 */
	@XmlEnumValue("ListIdentifiers")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	LIST_IDENTIFIERS,

	/**
	 * <code>ListRecords</code>
	 */
	@XmlEnumValue("ListRecords")
	@XmlSchemaType(name = "verbType", namespace = OaiPmh.NAMESPACE)
	LIST_RECORDS;
}
