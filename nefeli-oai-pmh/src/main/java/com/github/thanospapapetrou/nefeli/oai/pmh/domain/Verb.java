package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>verb</code> attribute of a <code>request</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = Verb.TYPE)
public enum Verb {
	/**
	 * <code>Identify</code>
	 */
	@XmlEnumValue("Identify")
	IDENTIFY,

	/**
	 * <code>ListMetadataFormats</code>
	 */
	@XmlEnumValue("ListMetadataFormats")
	LIST_METADATA_FORMATS,

	/**
	 * <code>ListSets</code>
	 */
	@XmlEnumValue("ListSets")
	LIST_SETS,

	/**
	 * <code>GetRecord</code>
	 */
	@XmlEnumValue("GetRecord")
	GET_RECORD,

	/**
	 * <code>ListIdentifiers</code>
	 */
	@XmlEnumValue("ListIdentifiers")
	LIST_IDENTIFIERS,

	/**
	 * <code>ListRecords</code>
	 */
	@XmlEnumValue("ListRecords")
	LIST_RECORDS;

	static final String TYPE = "verbType";
	
	@Override
	public String toString() {
		try {
			return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
}
