package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * Class representing a <code>metadata</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Metadata.TYPE, propOrder = {"element"})
public class Metadata {
	static final String TYPE = "metadataType";

	@XmlAnyElement
	private final Element element;

	/**
	 * Construct a new <code>metadata</code> element.
	 * 
	 * @param element
	 *            the nested element
	 */
	public Metadata(final Element element) {
		this.element = Objects.requireNonNull(element, "Element must not be null");
		this.element.normalize();
	}

	@SuppressWarnings("unused")
	private Metadata() {
		element = null;
	}

	/**
	 * Get the nested element.
	 * 
	 * @return the nested element
	 */
	public Element getElement() {
		return element;
	}

	@Override
	public boolean equals(final Object object) {
		return (object instanceof Metadata) && element.isEqualNode(((Metadata) object).element);
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}
}
