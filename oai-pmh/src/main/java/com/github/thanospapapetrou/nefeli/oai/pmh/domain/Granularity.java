package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining the <code>granularity</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "granularityType")
public enum Granularity {
	/**
	 * day granularity (<code>YYYY-MM-DD</code>)
	 */
	@XmlEnumValue("YYYY-MM-DD")
	YYYY_MM_DD,

	/**
	 * seconds granularity (<code>YYYY-MM-DDThh:mm:ssZ</code>)
	 */
	@XmlEnumValue("YYYY-MM-DDThh:mm:ssZ")
	YYYY_MM_DD_THH_MM_SS_Z;
}
