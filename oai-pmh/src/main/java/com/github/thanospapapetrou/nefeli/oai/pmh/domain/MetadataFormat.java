package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.net.URI;
import java.net.URL;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class representing a <code>metadataFormat</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataFormatType", propOrder = {"metadataPrefix", "schema", "metadataNamespace"})
public class MetadataFormat {
	@XmlElement(name = "metadataPrefix", required = true)
	@XmlSchemaType(name = "metadataPrefixType", namespace = OaiPmh.NAMESPACE)
	private final MetadataPrefix metadataPrefix;

	@XmlElement(name = "schema", required = true)
	@XmlSchemaType(name = "anyURI")
	private final URL schema;

	@XmlElement(name = "metadataNamespace", required = true)
	@XmlSchemaType(name = "anyURI")
	private final URI metadataNamespace;

	/**
	 * Construct a new <code>metadataFormat</code> element.
	 * 
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> element
	 * @param schema
	 *            the <code>schema</code> element
	 * @param metadataNamespace
	 *            the <code>metadataNamespace</code> element
	 */
	public MetadataFormat(final MetadataPrefix metadataPrefix, final URL schema, final URI metadataNamespace) {
		this.metadataPrefix = Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null");
		this.schema = Objects.requireNonNull(schema, "Schema must not be null");
		this.metadataNamespace = Objects.requireNonNull(metadataNamespace, "Metadata namespace must not be null");
	}

	@SuppressWarnings("unused")
	private MetadataFormat() {
		metadataPrefix = null;
		schema = null;
		metadataNamespace = null;
	}

	/**
	 * Get the <code>metadataPrefix</code> element.
	 * 
	 * @return the <code>metadataPrefix</code> element
	 */
	public MetadataPrefix getMetadataPrefix() {
		return metadataPrefix;
	}

	/**
	 * Get the <code>schema</code> element.
	 * 
	 * @return the <code>schema</code> element
	 */
	public URL getSchema() {
		return schema;
	}

	/**
	 * Get the <code>metadataNamespace</code> element.
	 * 
	 * @return the <code>metadataNamespace</code> element
	 */
	public URI getMetadataNamespace() {
		return metadataNamespace;
	}
}
