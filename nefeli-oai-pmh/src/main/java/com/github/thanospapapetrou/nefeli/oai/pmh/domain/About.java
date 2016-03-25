package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * Class representing an <code>about</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = About.TYPE, propOrder = {"any"})
public class About {
	static final String TYPE = "aboutType";
	
	@XmlAnyElement
	@XmlSchemaType(name = "anyType")
	private final Element element;

	/**
	 * Construct a new <code>about</code> element.
	 * 
	 * @param element
	 *            the nested element
	 */
	public About(final Element element) {
		this.element = Objects.requireNonNull(element, "Element must not be null");
	}

	@SuppressWarnings("unused")
	private About() {
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
}
