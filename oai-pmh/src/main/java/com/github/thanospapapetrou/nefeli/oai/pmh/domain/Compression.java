package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining a <code>compression</code> OAI-PMH element.
 * 
 * @author thanos
 * @see <a href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616 Hypertext Transfer Protocol -- HTTP/1.1</a>
 */
@XmlEnum
@XmlType(name = "string", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
public enum Compression {
	/**
	 * the gzip encoding format (<code>gzip</code>)
	 */
	@XmlEnumValue("gzip")
	GZIP,

	/**
	 * the compress encoding format (<code>compress</code>)
	 */
	@XmlEnumValue("compress")
	COMPRESS,

	/**
	 * the deflate encoding format (<code>deflate</code>)
	 */
	@XmlEnumValue("deflate")
	DEFLATE,

	/**
	 * the default (identity) encoding format (<code>identity</code>)
	 */
	@XmlEnumValue("identity")
	IDENTITY;

	@Override
	public String toString() {
		try {
			return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
		} catch (final NoSuchFieldException e) {
			return null;
		}
	}
}
