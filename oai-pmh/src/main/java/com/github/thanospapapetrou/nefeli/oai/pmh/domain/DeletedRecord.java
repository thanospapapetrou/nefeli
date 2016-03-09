package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>deletedRecord</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "deletedRecordType")
public enum DeletedRecord {
	/**
	 * <code>no</code>
	 */
	@XmlEnumValue("no")
	NO,
	
	/**
	 * <code>persistent</code>
	 */
	@XmlEnumValue("persistent")
	PERSISTENT,
	
	/**
	 * <code>transient</code>
	 */
	@XmlEnumValue("transient")
	TRANSIENT;
}
