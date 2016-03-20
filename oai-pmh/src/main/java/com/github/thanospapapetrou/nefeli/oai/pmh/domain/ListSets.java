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
 * Class representing a <code>ListSets</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ListSets.TYPE, propOrder = {"sets", "resumptionToken"})
public class ListSets {
	static final String TYPE = "ListSetsType";
	
	@XmlElement(name = "set", required = true)
	@XmlSchemaType(name = Set.TYPE, namespace = OaiPmh.NAMESPACE)
	private final List<Set> sets;

	@XmlElement(name = "resumptionToken")
	@XmlSchemaType(name = ResumptionToken.TYPE, namespace = OaiPmh.NAMESPACE)
	private final ResumptionToken resumptionToken;

	/**
	 * Construct a new <code>ListSets</code> element.
	 * 
	 * @param sets
	 *            the <code>set</code> elements
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> element or <code>null</code> to leave it unspecified
	 */
	public ListSets(final List<Set> sets, final ResumptionToken resumptionToken) {
		this.sets = new ArrayList<Set>(Objects.requireNonNull(sets, "Sets must not be null"));
		this.sets.removeAll(Collections.singleton(null));
		if (this.sets.isEmpty()) {
			throw new IllegalArgumentException("Sets must contain at least one non null element");
		}
		this.resumptionToken = resumptionToken;
	}

	/**
	 * Construct a new <code>ListSets</code> element with the <code>resumptionToken</code> element left unspecified
	 * 
	 * @param sets
	 *            the <code>set</code> elements
	 */
	public ListSets(final List<Set> sets) {
		this(sets, null);
	}

	@SuppressWarnings("unused")
	private ListSets() {
		sets = new ArrayList<Set>();
		resumptionToken = null;
	}

	/**
	 * Get the <code>set</code> elements.
	 * 
	 * @return the <code>set</code> elements
	 */
	public List<Set> getSets() {
		return Collections.unmodifiableList(sets);
	}

	/**
	 * Get the <code>resumptionToken</code> element.
	 * 
	 * @return the <code>resumptionToken</code> element or <code>null</code> if unspecified
	 */
	public ResumptionToken getResumptionToken() {
		return resumptionToken;
	}
}
