package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>status</code> attribute of a <code>header</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "statusType")
public enum Status {
	/**
	 * deleted record (<code>deleted</code>)
	 */
	@XmlEnumValue("deleted")
	DELETED;

	@Override
	public String toString() {
		try {
			return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
}
