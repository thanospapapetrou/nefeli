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
 * Class representing the <code>ListMetadataFormats</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListMetadataFormatsType", propOrder = {"metadataFormats"})
public class ListMetadataFormats {
	@XmlElement(name = "metadataFormat", required = true)
	@XmlSchemaType(name = "metadataFormatType", namespace = OaiPmh.NAMESPACE)
	private final List<MetadataFormat> metadataFormats; // TODO set?

	/**
	 * Construct a new <code>ListMetadataFormats</code> element.
	 * 
	 * @param metadataFormats
	 *            the <code>metadataFormat</code> elements
	 */
	public ListMetadataFormats(final List<MetadataFormat> metadataFormats) {
		this.metadataFormats = new ArrayList<MetadataFormat>(Objects.requireNonNull(metadataFormats, "Metadata formats must not be null"));
		this.metadataFormats.removeAll(Collections.singleton(null));
		if (this.metadataFormats.isEmpty()) {
			throw new IllegalArgumentException("Metadata formats must contain at least one non null element");
		}
	}

	@SuppressWarnings("unused")
	private ListMetadataFormats() {
		metadataFormats = new ArrayList<MetadataFormat>();
	}

	/**
	 * Get the <code>metadataFormat</code> elements.
	 * 
	 * @return the <code>metadataFormat</code> elements
	 */
	public List<MetadataFormat> getMetadataFormats() {
		return Collections.unmodifiableList(metadataFormats);
	}
}
