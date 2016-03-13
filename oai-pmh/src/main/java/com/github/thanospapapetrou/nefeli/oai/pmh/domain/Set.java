package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class representing a <code>set</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setType", propOrder = {"setSpec", "setName", "setDescriptions"})
public class Set {
	@XmlElement(name = "setSpec", required = true)
	@XmlSchemaType(name = "setSpecType", namespace = OaiPmh.NAMESPACE)
	private final SetSpec setSpec;

	@XmlElement(name = "setName", required = true)
	@XmlSchemaType(name = "string")
	private final String setName;

	@XmlElement(name = "setDescription")
	@XmlSchemaType(name = "descriptionType", namespace = OaiPmh.NAMESPACE)
	private final List<Description> setDescriptions; // TODO should it be set?

	/**
	 * Construct a new <code>set</code>element.
	 * 
	 * @param setSpec
	 *            the <code>setSpec</code> element
	 * @param setName
	 *            the <code>setName</code> element
	 * @param setDescriptions
	 *            the <code>setDescription</code> elements or <code>null</code> to leave them unspecified
	 */
	public Set(final SetSpec setSpec, final String setName, final List<Description> setDescriptions) {
		this.setSpec = Objects.requireNonNull(setSpec, "Set spec must not be null");
		this.setName = Objects.requireNonNull(setName, "Set name must not be null");
		this.setDescriptions = (setDescriptions == null) ? new ArrayList<Description>() : new ArrayList<Description>(setDescriptions);
		this.setDescriptions.removeAll(Collections.singleton(null));
	}

	/**
	 * Construct a new <code>set</code>element with the <code>setDescription</code> elements left unspecified.
	 * 
	 * @param setSpec
	 *            the <code>setSpec</code> element
	 * @param setName
	 *            the <code>setName</code> element
	 */
	public Set(final SetSpec setSpec, final String setName) {
		this(setSpec, setName, null);
	}

	/**
	 * Get the <code>setSpec</code> element.
	 * 
	 * @return the <code>setSpec</code> element
	 */
	public SetSpec getSetSpec() {
		return setSpec;
	}

	/**
	 * Get the <code>setName<code> element.
	 * 
	 * @return the <code>setName<code> element
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * Get the <code>setDescription</code> elements.
	 * 
	 * @return the <code>setDescription</code> elements
	 */
	public List<Description> getSetDescriptions() {
		return Collections.unmodifiableList(setDescriptions);
	}
}
