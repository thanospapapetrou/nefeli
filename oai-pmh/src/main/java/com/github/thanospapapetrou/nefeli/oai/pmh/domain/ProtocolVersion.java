package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining a <code>protocolVersion</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "protocolVersionType")
public enum ProtocolVersion {
	/**
	 * OAI-PMH 2.0 (<code>2.0</code>)
	 */
	@XmlEnumValue("2.0")
	OAI_PMH_2_0;

	@Override
	public String toString() {
		try {
			return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
}
