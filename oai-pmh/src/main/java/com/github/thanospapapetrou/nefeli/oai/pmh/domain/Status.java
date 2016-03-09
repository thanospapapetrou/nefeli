package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>status</code> OAI-PMH attribute.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "statusType")
public enum Status {
	/**
	 * <code>deleted</code>
	 */
	@XmlEnumValue("deleted")
	@XmlSchemaType(name = "statusType", namespace = OaiPmh.NAMESPACE)
	DELETED;
}
