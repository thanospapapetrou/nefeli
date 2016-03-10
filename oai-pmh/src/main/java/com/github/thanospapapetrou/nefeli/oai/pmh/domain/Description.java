package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * Class representing the <code>description</code> and <code>setDescription</code> OAI-PMH elements.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "descriptionType", propOrder = {"any"})
public class Description {
	@XmlAnyElement
	@XmlSchemaType(name = "anyType")
	private final Element element;

	/**
	 * Construct a new <code>description</code> or <code>setDescription</code> element.
	 * 
	 * @param element
	 *            the nested element
	 */
	public Description(final Element element) {
		this.element = Objects.requireNonNull(element, "Element must not be null");
	}

	@SuppressWarnings("unused")
	private Description() {
		element = null;
	}

	/**
	 * Get the nested element.
	 * 
	 * @return the nested element
	 */
	public Object getElement() {
		return element;
	}
}
