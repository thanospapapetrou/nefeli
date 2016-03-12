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
	 * the repository does not maintain information about deletions (<code>no</code>)
	 */
	@XmlEnumValue("no")
	NO,
	
	/**
	 * the repository maintains information about deletions with no time limit (<code>persistent</code>)
	 */
	@XmlEnumValue("persistent")
	PERSISTENT,
	
	/**
	 * the repository does not guarantee that a list of deletions is maintained persistently or consistently (<code>transient</code>)
	 */
	@XmlEnumValue("transient")
	TRANSIENT;

	@Override
	public String toString() {
		try {
			return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
}
