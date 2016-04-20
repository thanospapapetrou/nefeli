package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.mail.internet.InternetAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampGranularityXmlAdapter;

/**
 * Class representing an <code>Identify</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Identify.TYPE, propOrder = {"repositoryName", "baseUrl", "protocolVersion", "adminEmails", "earliestDatestamp", "deletedRecord", "granularity", "compressions", "descriptions"})
public class Identify {
	static final String TYPE = "IdentifyType";

	@XmlElement(name = "repositoryName", required = true)
	@XmlSchemaType(name = "string")
	private final String repositoryName;

	@XmlElement(name = "baseURL", required = true)
	@XmlSchemaType(name = "anyURI")
	private final URL baseUrl;

	@XmlElement(name = "protocolVersion", required = true)
	@XmlSchemaType(name = ProtocolVersion.TYPE, namespace = OaiPmh.NAMESPACE)
	private final ProtocolVersion protocolVersion;

	@XmlElement(name = "adminEmail", required = true)
	@XmlSchemaType(name = "emailType", namespace = OaiPmh.NAMESPACE)
	private final List<InternetAddress> adminEmails;

	@XmlElement(name = "earliestDatestamp", required = true)
	@XmlSchemaType(name = OaiPmh.UTC_DATETIME_TYPE, namespace = OaiPmh.NAMESPACE)
	@XmlJavaTypeAdapter(DatestampGranularityXmlAdapter.class)
	private final Date earliestDatestamp;

	@XmlElement(name = "deletedRecord", required = true)
	@XmlSchemaType(name = DeletedRecord.TYPE, namespace = OaiPmh.NAMESPACE)
	private final DeletedRecord deletedRecord;

	@XmlElement(name = "granularity", required = true)
	@XmlSchemaType(name = Granularity.TYPE, namespace = OaiPmh.NAMESPACE)
	private final Granularity granularity;

	@XmlElement(name = "compression")
	@XmlSchemaType(name = Compression.TYPE)
	private final List<Compression> compressions;

	@XmlElement(name = "description")
	@XmlSchemaType(name = Description.TYPE, namespace = OaiPmh.NAMESPACE)
	private final List<Description> descriptions;

	/**
	 * Construct a new <code>Identify</code> element.
	 * 
	 * @param repositoryName
	 *            the <code>repositoryName</code> element
	 * @param baseUrl
	 *            the <code>baseURL</code> element
	 * @param adminEmails
	 *            the <code>adminEmail</code> elements
	 * @param earliestDatestamp
	 *            the <code>earliestDatestamp</code> element
	 * @param deletedRecord
	 *            the <code>deletedRecord</code> element
	 * @param granularity
	 *            the <code>granularity</code> element
	 * @param compressions
	 *            the <code>compression</code> elements or <code>null</code> to leave them unspecified
	 * @param descriptions
	 *            the <code>description</code> elements or <code>null</code> to leave them unspecified
	 */
	public Identify(final String repositoryName, final URL baseUrl, final List<InternetAddress> adminEmails, final Date earliestDatestamp, final DeletedRecord deletedRecord, final Granularity granularity, final List<Compression> compressions, final List<Description> descriptions) {
		this.repositoryName = Objects.requireNonNull(repositoryName, "Repository name must not be null");
		this.baseUrl = Objects.requireNonNull(baseUrl, "Base URL must not be null");
		protocolVersion = ProtocolVersion.OAI_PMH_2_0;
		this.adminEmails = Objects.requireNonNull(adminEmails, "Admin emails must not be null");
		this.adminEmails.removeAll(Collections.singleton(null));
		if (this.adminEmails.isEmpty()) {
			throw new IllegalArgumentException("Admin emails must contain at least one non null element");
		}
		this.earliestDatestamp = Objects.requireNonNull(earliestDatestamp, "Earliest datestamp must not be null");
		this.deletedRecord = Objects.requireNonNull(deletedRecord, "Deleted record must not be null");
		this.granularity = Objects.requireNonNull(granularity, "Granularity must not be null");
		this.compressions = (compressions == null) ? new ArrayList<Compression>() : compressions;
		this.compressions.removeAll(Collections.singleton(null));
		this.descriptions = (descriptions == null) ? new ArrayList<Description>() : descriptions;
		this.descriptions.removeAll(Collections.singleton(null));
	}

	/**
	 * Construct a new <code>Identify</code> element with the <code>compression</code> and the <code>description</code> elements left unspecified.
	 * 
	 * @param repositoryName
	 *            the <code>repositoryName</code> element
	 * @param baseUrl
	 *            the <code>baseURL</code> element
	 * @param adminEmails
	 *            the <code>adminEmail</code> elements
	 * @param earliestDatestamp
	 *            the <code>earliestDatestamp</code> element
	 * @param deletedRecord
	 *            the <code>deletedRecord</code> element
	 * @param granularity
	 *            the <code>granularity</code> element
	 */
	public Identify(final String repositoryName, final URL baseUrl, final List<InternetAddress> adminEmails, final Date earliestDatestamp, final DeletedRecord deletedRecord, final Granularity granularity) {
		this(repositoryName, baseUrl, adminEmails, earliestDatestamp, deletedRecord, granularity, null, null);
	}

	@SuppressWarnings("unused")
	private Identify() {
		repositoryName = null;
		baseUrl = null;
		protocolVersion = null;
		adminEmails = new ArrayList<InternetAddress>();
		earliestDatestamp = null;
		deletedRecord = null;
		granularity = null;
		compressions = new ArrayList<Compression>();
		descriptions = new ArrayList<Description>();
	}

	/**
	 * Get the <code>repositoryName<code> element.
	 * 
	 * @return the <code>repositoryName<code> element
	 */
	public String getRepositoryName() {
		return repositoryName;
	}

	/**
	 * Get the <code>baseURL<code> element.
	 * 
	 * @return the <code>baseURL<code> element
	 */
	public URL getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Get the <code>protocolVersion</code> element.
	 * 
	 * @return the <code>protocolVersion</code> element
	 */
	public ProtocolVersion getProtocolVersion() {
		return protocolVersion;
	}

	/**
	 * Get the <code>adminEmail</code> elements.
	 * 
	 * @return the <code>adminEmail</code> elements
	 */
	public List<InternetAddress> getAdminEmails() {
		return Collections.unmodifiableList(adminEmails);
	}

	/**
	 * Get the <code>earliestDatestamp</code> element.
	 * 
	 * @return the <code>earliestDatestamp</code> element
	 */
	public Date getEarliestDatestamp() {
		return earliestDatestamp;
	}

	/**
	 * Get the <code>deletedRecord</code> element.
	 * 
	 * @return the <code>deletedRecord</code> element
	 */
	public DeletedRecord getDeletedRecord() {
		return deletedRecord;
	}

	/**
	 * Get the <code>granularity</code> element.
	 * 
	 * @return the <code>granularity</code> element
	 */
	public Granularity getGranularity() {
		return granularity;
	}

	/**
	 * Get the <code>compression</code> elements.
	 * 
	 * @return the <code>compression</code> elements
	 */
	public List<Compression> getCompressions() {
		return Collections.unmodifiableList(compressions);
	}

	/**
	 * Get the <code>description</code> elements.
	 * 
	 * @return the <code>description</code> elements
	 */
	public List<Description> getDescriptions() {
		return Collections.unmodifiableList(descriptions);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Identify) {
			final Identify identify = (Identify) object;
			return repositoryName.equals(identify.repositoryName) && baseUrl.equals(identify.baseUrl) && protocolVersion.equals(identify.protocolVersion) && adminEmails.equals(identify.adminEmails) && earliestDatestamp.equals(identify.earliestDatestamp) && deletedRecord.equals(identify.deletedRecord) && granularity.equals(identify.granularity) && compressions.equals(identify.compressions) && descriptions.equals(identify.descriptions);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(repositoryName, baseUrl, protocolVersion, adminEmails, earliestDatestamp, deletedRecord, granularity, compressions, descriptions);
	}
}
