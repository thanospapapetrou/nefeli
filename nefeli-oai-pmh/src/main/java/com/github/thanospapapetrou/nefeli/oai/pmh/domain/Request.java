package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampGranularityXmlAdapter;

/**
 * Class representing a <code>request</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Request.TYPE, propOrder = {"baseUrl"})
public class Request {
	static final String TYPE = "requestType";

	@XmlAttribute(name = "verb")
	@XmlSchemaType(name = Verb.TYPE, namespace = OaiPmh.NAMESPACE)
	private final Verb verb;

	@XmlAttribute(name = "identifier")
	@XmlSchemaType(name = OaiPmh.IDENTIFIER_TYPE, namespace = OaiPmh.NAMESPACE)
	private final URI identifier;

	@XmlAttribute(name = "metadataPrefix")
	@XmlSchemaType(name = MetadataPrefix.TYPE, namespace = OaiPmh.NAMESPACE)
	private final MetadataPrefix metadataPrefix;

	@XmlAttribute(name = "from")
	@XmlSchemaType(name = OaiPmh.UTC_DATETIME_TYPE, namespace = OaiPmh.NAMESPACE)
	@XmlJavaTypeAdapter(DatestampGranularityXmlAdapter.class)
	private final Date from;

	@XmlAttribute(name = "until")
	@XmlSchemaType(name = OaiPmh.UTC_DATETIME_TYPE, namespace = OaiPmh.NAMESPACE)
	@XmlJavaTypeAdapter(DatestampGranularityXmlAdapter.class)
	private final Date until;

	@XmlAttribute(name = "set")
	@XmlSchemaType(name = SetSpec.TYPE, namespace = OaiPmh.NAMESPACE)
	private final SetSpec set;

	@XmlAttribute(name = "resumptionToken")
	@XmlSchemaType(name = "string")
	private final String resumptionToken;

	@XmlValue
	@XmlSchemaType(name = "anyURI")
	private final URL baseUrl;

	/**
	 * Construct a new <code>request<code> element corresponding to an <code>Identify</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @return a new <code>request</code> element
	 */
	public static Request identify(final URL baseUrl) {
		return new Request(Verb.IDENTIFY, null, null, null, null, null, null, baseUrl);
	}

	/**
	 * Construct a new <code>request<code> element possibly with an <code>identifier</code> attribute corresponding to a <code>ListMetadataFormats</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param identifier
	 *            the <code>identifier</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listMetadataFormats(final URL baseUrl, final URI identifier) {
		return new Request(Verb.LIST_METADATA_FORMATS, identifier, null, null, null, null, null, baseUrl);
	}

	/**
	 * Construct a new <code>request<code> element corresponding to a <code>ListMetadataFormats</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @return a new <code>request</code> element
	 */
	public static Request listMetadataFormats(final URL baseUrl) {
		return listMetadataFormats(baseUrl, null);
	}

	/**
	 * Construct a new <code>request<code> element corresponding to a <code>ListSets</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @return a new <code>request</code> element
	 */
	public static Request listSets(final URL baseUrl) {
		return listSets(baseUrl, null);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>resumptionToken</code> attribute corresponding to a <code>ListSets</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request listSets(final URL baseUrl, final String resumptionToken) {
		return new Request(Verb.LIST_SETS, null, null, null, null, null, Objects.requireNonNull(resumptionToken, "Resumption token must not be null"), baseUrl);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>metadataPrefix</code> attribute and possibly <code>from</code>, <code>until</code> and <code>set</code> attributes corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param from
	 *            the <code>from</code> attribute or <code>null</code> to leave it unspecified
	 * @param until
	 *            the <code>until</code> attribute or <code>null</code> to leave it unspecified
	 * @param set
	 *            the <code>set</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listIdentifiers(final URL baseUrl, final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) {
		return new Request(Verb.LIST_IDENTIFIERS, null, Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null"), from, until, set, null, baseUrl);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>metadataPrefix</code> attribute and possibly <code>from</code> and <code>until</code> attributes corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param from
	 *            the <code>from</code> attribute or <code>null</code> to leave it unspecified
	 * @param until
	 *            the <code>until</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listIdentifiers(final URL baseUrl, final MetadataPrefix metadataPrefix, final Date from, final Date until) {
		return listIdentifiers(baseUrl, metadataPrefix, from, until, null);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>metadataPrefix</code> and possibly a <code>set</code> attribute corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param set
	 *            the <code>set</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listIdentifiers(final URL baseUrl, final MetadataPrefix metadataPrefix, final SetSpec set) {
		return listIdentifiers(baseUrl, metadataPrefix, null, null, set);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>metadataPrefix</code> attribute corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request listIdentifiers(final URL baseUrl, final MetadataPrefix metadataPrefix) {
		return listIdentifiers(baseUrl, metadataPrefix, null);
	}

	/**
	 * Construct a new <code>request<code> element with a <code>resumptionToken</code> attribute corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request listIdentifiers(final URL baseUrl, final String resumptionToken) {
		return new Request(Verb.LIST_IDENTIFIERS, null, null, null, null, null, Objects.requireNonNull(resumptionToken, "Resumption token must not be null"), baseUrl);
	}

	/**
	 * Construct a new <code>request</code> element with a <code>metadataPrefix</code> attribute and possibly <code>from</code>, <code>until</code> and <code>set</code> attributes corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param from
	 *            the <code>from</code> attribute or <code>null</code> to leave it unspecified
	 * @param until
	 *            the <code>until</code> attribute or <code>null</code> to leave it unspecified
	 * @param set
	 *            the <code>set</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listRecords(final URL baseUrl, final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) {
		return new Request(Verb.LIST_RECORDS, null, Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null"), from, until, set, null, baseUrl);
	}

	/**
	 * Construct a new <code>request</code> element with a <code>metadataPrefix</code> attribute and possibly <code>from</code> and <code>until</code> attributes corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param from
	 *            the <code>from</code> attribute or <code>null</code> to leave it unspecified
	 * @param until
	 *            the <code>until</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listRecords(final URL baseUrl, final MetadataPrefix metadataPrefix, final Date from, final Date until) {
		return listRecords(baseUrl, metadataPrefix, from, until, null);
	}

	/**
	 * Construct a new <code>request</code> element with a <code>metadataPrefix</code> attribute and possibly a <code>set</code> attribute corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @param set
	 *            the <code>set</code> attribute or <code>null</code> to leave it unspecified
	 * @return a new <code>request</code> element
	 */
	public static Request listRecords(final URL baseUrl, final MetadataPrefix metadataPrefix, final SetSpec set) {
		return listRecords(baseUrl, metadataPrefix, null, null, set);
	}

	/**
	 * Construct a new <code>request</code> element with a <code>metadataPrefix</code> attribute corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request listRecords(final URL baseUrl, final MetadataPrefix metadataPrefix) {
		return listRecords(baseUrl, metadataPrefix, null);
	}

	/**
	 * Construct a new <code>request</code> element with a <code>resumptionToken</code> attribute corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param resumptionToken
	 *            the <code>resumptionToken</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request listRecords(final URL baseUrl, final String resumptionToken) {
		return new Request(Verb.LIST_RECORDS, null, null, null, null, null, Objects.requireNonNull(resumptionToken, "Resumption token must not be null"), baseUrl);
	}

	/**
	 * Construct a new <code>request</code> element corresponding to a <code>GetRecord</code> OAI-PMH request.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @param identifier
	 *            the <code>identifier</code> attribute
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> attribute
	 * @return a new <code>request</code> element
	 */
	public static Request getRecord(final URL baseUrl, final URI identifier, final MetadataPrefix metadataPrefix) {
		return new Request(Verb.GET_RECORD, Objects.requireNonNull(identifier, "Identifier must not be null"), Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null"), null, null, null, null, baseUrl);
	}

	/**
	 * Construct a new <code>request</code> element corresponding to a <code>badVerb</code> or a <code>badArgument</code> OAI-PMH error.
	 * 
	 * @param baseUrl
	 *            the base URL
	 * @return a new <code>request</code> element
	 */
	public static Request error(final URL baseUrl) {
		return new Request(null, null, null, null, null, null, null, baseUrl);
	}

	private Request(final Verb verb, final URI identifier, final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set, final String resumptionToken, final URL baseUrl) {
		this.verb = verb;
		this.identifier = identifier;
		this.metadataPrefix = metadataPrefix;
		this.from = from;
		this.until = until;
		this.set = set;
		this.resumptionToken = resumptionToken;
		this.baseUrl = Objects.requireNonNull(baseUrl, "Base URL must not be null");
	}

	private Request() {
		verb = null;
		identifier = null;
		metadataPrefix = null;
		from = null;
		until = null;
		set = null;
		resumptionToken = null;
		baseUrl = null;
	}

	/**
	 * Get the <code>verb</code> attribute.
	 * 
	 * @return the <code>verb</code> attribute or <code>null</code> if unspecified
	 */
	public Verb getVerb() {
		return verb;
	}

	/**
	 * Get the <code>identifier</code> attribute.
	 * 
	 * @return the <code>identifier</code> attribute or <code>null</code> if unspecified
	 */
	public URI getIdentifier() {
		return identifier;
	}

	/**
	 * Get the <code>metadataPrefix</code> attribute.
	 * 
	 * @return the <code>metadataPrefix</code> attribute or <code>null</code> if unspecified
	 */
	public MetadataPrefix getMetadataPrefix() {
		return metadataPrefix;
	}

	/**
	 * Get the <code>from</code> attribute.
	 * 
	 * @return the <code>from</code> attribute or <code>null</code> if unspecified
	 */
	public Date getFrom() {
		return from;
	}

	/**
	 * Get the <code>until</code> attribute.
	 * 
	 * @return the <code>until</code> attribute or <code>null</code> if unspecified
	 */
	public Date getUntil() {
		return until;
	}

	/**
	 * Get the <code>set</code> attribute.
	 * 
	 * @return the <code>set</code> attribute or <code>null</code> if unspecified
	 */
	public SetSpec getSet() {
		return set;
	}

	/**
	 * Get the <code>resumptionToken</code> attribute.
	 * 
	 * @return the <code>resumptionToken</code> attribute or <code>null</code> if unspecified
	 */
	public String getResumptionToken() {
		return resumptionToken;
	}

	/**
	 * Get the base URL.
	 * 
	 * @return the base URL
	 */
	public URL getBaseUrl() {
		return baseUrl;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Request) {
			final Request request = (Request) object;
			return Objects.equals(verb, request.verb) && Objects.equals(identifier, request.identifier) && Objects.equals(metadataPrefix, request.metadataPrefix) && Objects.equals(from, request.from) && Objects.equals(until, request.until) && Objects.equals(set, request.set) && Objects.equals(resumptionToken, request.resumptionToken) && baseUrl.equals(request.baseUrl);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(verb, identifier, metadataPrefix, from, until, set, resumptionToken, baseUrl);
	}
}
