package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

import com.github.thanospapapetrou.nefeli.commons.NodeComparator;

/**
 * Class representing an <code>about</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = About.TYPE, propOrder = {"element"})
public class About {
	static final String TYPE = "aboutType";
	private static final NodeComparator NODE_COMPARATOR = new NodeComparator();

	@XmlAnyElement
	private final Element element;

	/**
	 * Construct a new <code>about</code> element.
	 * 
	 * @param element
	 *            the nested element
	 */
	public About(final Element element) {
		this.element = Objects.requireNonNull(element, "Element must not be null");
		this.element.normalize();
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

	@Override
	public boolean equals(final Object object) {
		return (object instanceof About) && NODE_COMPARATOR.equals(element, ((About) object).element);
	}

	@Override
	public int hashCode() {
		return NODE_COMPARATOR.hashCode(element);
	}
}
