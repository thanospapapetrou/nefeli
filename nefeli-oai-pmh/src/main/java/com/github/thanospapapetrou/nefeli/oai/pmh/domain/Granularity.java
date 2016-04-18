package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining a <code>granularity</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = Granularity.TYPE)
public enum Granularity {
	/**
	 * day granularity (<code>YYYY-MM-DD</code>)
	 */
	@XmlEnumValue("YYYY-MM-DD")
	DAY,

	/**
	 * seconds granularity (<code>YYYY-MM-DDThh:mm:ssZ</code>)
	 */
	@XmlEnumValue("YYYY-MM-DDThh:mm:ssZ")
	SECONDS;

	static final String TYPE = "granularityType";

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
