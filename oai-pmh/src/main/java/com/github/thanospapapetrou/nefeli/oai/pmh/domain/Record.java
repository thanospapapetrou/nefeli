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
 * Class representing a <code>record</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", propOrder = {"header", "metadata", "abouts"})
public class Record {
	@XmlElement(name = "header", required = true)
	@XmlSchemaType(name = "headerType", namespace = OaiPmh.NAMESPACE)
	private final Header header;

	@XmlElement(name = "metadata")
	@XmlSchemaType(name = "metadataType", namespace = OaiPmh.NAMESPACE)
	private final Metadata metadata;

	@XmlElement(name = "about")
	@XmlSchemaType(name = "aboutType", namespace = OaiPmh.NAMESPACE)
	private final List<About> abouts;

	/**
	 * Construct a new <code>record</code> element.
	 * 
	 * @param header
	 *            the <code>header</code> element
	 * @param metadata
	 *            the <code>metadata</code> element or <code>null</code> to leave it unspecified
	 * @param abouts
	 *            the <code>about</code> elements or <code>null</code> to leave them unspecified
	 */
	public Record(final Header header, final Metadata metadata, final List<About> abouts) {
		this.header = Objects.requireNonNull(header, "Header must not be null");
		this.metadata = metadata;
		this.abouts = (abouts == null) ? new ArrayList<About>() : new ArrayList<About>(abouts);
	}

	/**
	 * Construct a new <code>record</code> element with <code>about</code> elements left unspecified.
	 * 
	 * @param header
	 *            the <code>header</code> element
	 * @param metadata
	 *            the <code>metadata</code> element or <code>null</code> to leave it unspecified
	 */
	public Record(final Header header, final Metadata metadata) {
		this(header, metadata, null);
	}

	/**
	 * Construct a new <code>record</code> element with <code>metadata</code> element left unspecified.
	 * 
	 * @param header
	 *            the <code>header</code> element
	 * @param abouts
	 *            the <code>about</code> elements or <code>null</code> to leave them unspecified
	 */
	public Record(final Header header, final List<About> abouts) {
		this(header, null, abouts);
	}

	/**
	 * Construct a new <code>record</code> element with <code>metadata</code> and <code>about</code> elements left unspecified.
	 * 
	 * @param header
	 *            the <code>header</code> element
	 */
	public Record(final Header header) {
		this(header, null, null);
	}

	@SuppressWarnings("unused")
	private Record() {
		header = null;
		metadata = null;
		abouts = new ArrayList<About>();
	}

	/**
	 * Get the <code>header</code> element.
	 * 
	 * @return the <code>header</code> element
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * Get the <code>metadata</code> element.
	 * 
	 * @return the <code>metadata</code> element or <code>null</code> if unspecified
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * Get the <code>about</code> elements.
	 * 
	 * @return the <code>about</code> elements
	 */
	public List<About> getAbouts() {
		return Collections.unmodifiableList(abouts);
	}
}
