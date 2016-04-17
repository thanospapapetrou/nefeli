package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * Class representing a <code>description</code> or a <code>setDescription</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Description.TYPE, propOrder = {"element"})
public class Description {
	static final String TYPE = "descriptionType";

	@XmlAnyElement
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

	@Override
	public boolean equals(final Object object) {
		return (object instanceof Description) && element.isEqualNode(((Description) object).element);
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}
}
