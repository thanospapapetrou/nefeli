package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampGranularityXmlAdapter;

/**
 * Class representing a <code>header</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Header.TYPE, propOrder = {"identifier", "datestamp", "setSpecs"})
public class Header {
	static final String TYPE = "headerType";

	@XmlAttribute(name = "status")
	@XmlSchemaType(name = Status.TYPE, namespace = OaiPmh.NAMESPACE)
	private final Status status;

	@XmlElement(name = "identifier", required = true)
	@XmlSchemaType(name = OaiPmh.IDENTIFIER_TYPE, namespace = OaiPmh.NAMESPACE)
	private final URI identifier;

	@XmlElement(name = "datestamp", required = true)
	@XmlSchemaType(name = OaiPmh.UTC_DATETIME_TYPE, namespace = OaiPmh.NAMESPACE)
	@XmlJavaTypeAdapter(DatestampGranularityXmlAdapter.class)
	private final Date datestamp;

	@XmlElement(name = "setSpec")
	@XmlSchemaType(name = SetSpec.TYPE, namespace = OaiPmh.NAMESPACE)
	private final List<SetSpec> setSpecs;

	/**
	 * Construct a new <code>header</code> element.
	 * 
	 * @param status
	 *            the <code>status</code> attribute or <code>null</code> to leave it unspecified
	 * @param identifier
	 *            the <code>identifier</code> element
	 * @param datestamp
	 *            the <code>datestamp</code> element
	 * @param setSpecs
	 *            the <code>setSpec</code> elements or <code>null</code> to leave them unspecified
	 */
	public Header(final Status status, final URI identifier, final Date datestamp, final List<SetSpec> setSpecs) {
		this.status = status;
		this.identifier = Objects.requireNonNull(identifier, "Identifier must not be null");
		this.datestamp = Objects.requireNonNull(datestamp, "Datestamp must not be null");
		this.setSpecs = (setSpecs == null) ? new ArrayList<SetSpec>() : setSpecs;
		this.setSpecs.removeAll(Collections.singleton(null));
	}

	/**
	 * Construct a new <code>header</code> element with the <code>setSpec</code> elements left unspecified.
	 * 
	 * @param status
	 *            the <code>status</code> attribute or <code>null</code> to leave it unspecified
	 * @param identifier
	 *            the <code>identifier</code> element
	 * @param datestamp
	 *            the <code>datestamp</code> element
	 */
	public Header(final Status status, final URI identifier, final Date datestamp) {
		this(status, identifier, datestamp, null);
	}

	/**
	 * Construct a new <code>header</code> element with the <code>status</code> attribute left unspecified.
	 * 
	 * @param identifier
	 *            the <code>identifier</code> element
	 * @param datestamp
	 *            the <code>datestamp</code> element
	 * @param setSpecs
	 *            the <code>setSpec</code> elements or <code>null</code> to leave them unspecified
	 */
	public Header(final URI identifier, final Date datestamp, final List<SetSpec> setSpecs) {
		this(null, identifier, datestamp, setSpecs);
	}

	/**
	 * Construct a new <code>header</code> element with the <code>status</code> attribute and the <code>setSpec</code> elements left unspecified.
	 * 
	 * @param identifier
	 *            the <code>identifier</code> element
	 * @param datestamp
	 *            the <code>datestamp</code> element
	 */
	public Header(final URI identifier, final Date datestamp) {
		this(null, identifier, datestamp);
	}

	@SuppressWarnings("unused")
	private Header() {
		status = null;
		identifier = null;
		datestamp = null;
		setSpecs = new ArrayList<SetSpec>();
	}

	/**
	 * Get the <code>status</code> attribute.
	 * 
	 * @return the <code>status</code> attribute or <code>null</code> if unspecified
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Get the <code>identifier</code> element.
	 * 
	 * @return the <code>identifier</code> element
	 */
	public URI getIdentifier() {
		return identifier;
	}

	/**
	 * Get the <code>datestamp</code> element.
	 * 
	 * @return the <code>datestamp</code> element
	 */
	public Date getDatestamp() {
		return datestamp;
	}

	/**
	 * Get the <code>setSpec</code> elements.
	 * 
	 * @return the <code>setSpec</code> elements
	 */
	public List<SetSpec> getSetSpecs() {
		return Collections.unmodifiableList(setSpecs);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Header) {
			final Header header = (Header) object;
			return Objects.equals(status, header.status) && identifier.equals(header.identifier) && datestamp.equals(header.datestamp) && setSpecs.equals(header.setSpecs);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, identifier, datestamp, setSpecs);
	}
}
