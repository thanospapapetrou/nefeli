package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters.DatestampSecondsGranularityXmlAdapter;

/**
 * Class representing an <code>OAI-PMH</code> element.
 * 
 * @author thanos
 */
@XmlRootElement(name = "OAI-PMH")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OAI-PMHtype", propOrder = {"responseDate", "request", "errors", "identify", "listMetadataFormats", "listSets", "getRecord", "listIdentifiers", "listRecords"})
public class OaiPmhResponse {
	static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/";
	static final String IDENTIFIER_TYPE = "identifierType";
	static final String UTC_DATETIME_TYPE = "UTCdatetimeType";

	@XmlElement(name = "responseDate", required = true)
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DatestampSecondsGranularityXmlAdapter.class)
	private final Date responseDate;

	@XmlElement(name = "request", required = true)
	@XmlSchemaType(name = Request.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final Request request;

	@XmlElement(name = "error")
	@XmlSchemaType(name = Error.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final List<Error> errors;

	@XmlElement(name = "Identify")
	@XmlSchemaType(name = Identify.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final Identify identify;

	@XmlElement(name = "ListMetadataFormats")
	@XmlSchemaType(name = ListMetadataFormats.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final ListMetadataFormats listMetadataFormats;

	@XmlElement(name = "ListSets")
	@XmlSchemaType(name = ListSets.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final ListSets listSets;

	@XmlElement(name = "GetRecord")
	@XmlSchemaType(name = GetRecord.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final GetRecord getRecord;

	@XmlElement(name = "ListIdentifiers")
	@XmlSchemaType(name = ListIdentifiers.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final ListIdentifiers listIdentifiers;

	@XmlElement(name = "ListRecords")
	@XmlSchemaType(name = ListRecords.TYPE, namespace = OaiPmhResponse.NAMESPACE)
	private final ListRecords listRecords;

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to an OAI-PMH error.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param errors
	 *            the <code>error</code> elements
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final List<Error> errors) {
		this(responseDate, request, Objects.requireNonNull(errors, "Errors must not be null"), null, null, null, null, null, null);
		if (this.errors.isEmpty()) {
			throw new IllegalArgumentException("Errors must contain at least one non null element");
		}
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to an <code>Identify</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param identify
	 *            the <code>Identify</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final Identify identify) {
		this(responseDate, request, null, Objects.requireNonNull(identify, "Identify must not be null"), null, null, null, null, null);
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to a <code>ListMetadataFormats</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param listMetadataFormats
	 *            the <code>ListMetadataFormats</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final ListMetadataFormats listMetadataFormats) {
		this(responseDate, request, null, null, Objects.requireNonNull(listMetadataFormats, "List metadata formats must not be null"), null, null, null, null);
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to a <code>ListSets</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param listSets
	 *            the <code>ListSets</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final ListSets listSets) {
		this(responseDate, request, null, null, null, Objects.requireNonNull(listSets, "List sets must not be null"), null, null, null);
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to a <code>GetRecord</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param getRecord
	 *            the <code>GetRecord</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final GetRecord getRecord) {
		this(responseDate, request, null, null, null, null, Objects.requireNonNull(getRecord, "Get record must not be null"), null, null);
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to a <code>ListIdentifiers</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param listIdentifiers
	 *            the <code>ListIdentifiers</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final ListIdentifiers listIdentifiers) {
		this(responseDate, request, null, null, null, null, null, Objects.requireNonNull(listIdentifiers, "List identifiers must not be null"), null);
	}

	/**
	 * Construct a new <code>OAI-PMH</code> element corresponding to a <code>ListRecords</code> OAI-PMH request.
	 * 
	 * @param responseDate
	 *            the <code>responseDate</code> element
	 * @param request
	 *            the <code>request</code> element
	 * @param listRecords
	 *            the <code>ListRecords</code> element
	 */
	public OaiPmhResponse(final Date responseDate, final Request request, final ListRecords listRecords) {
		this(responseDate, request, null, null, null, null, null, null, Objects.requireNonNull(listRecords, "List records must not be null"));
	}

	private OaiPmhResponse(final Date responseDate, final Request request, final List<Error> errors, final Identify identify, final ListMetadataFormats listMetadataFormats, final ListSets listSets, final GetRecord getRecord, final ListIdentifiers listIdentifiers, final ListRecords listRecords) {
		this.responseDate = Objects.requireNonNull(responseDate, "Response date must not be null");
		this.request = Objects.requireNonNull(request, "Request must not be null");
		this.errors = (errors == null) ? new ArrayList<Error>() : errors;
		this.errors.removeAll(Collections.singleton(null));
		this.identify = identify;
		this.listMetadataFormats = listMetadataFormats;
		this.listSets = listSets;
		this.getRecord = getRecord;
		this.listIdentifiers = listIdentifiers;
		this.listRecords = listRecords;
	}

	@SuppressWarnings("unused")
	private OaiPmhResponse() {
		responseDate = null;
		request = null;
		errors = new ArrayList<Error>();
		identify = null;
		listMetadataFormats = null;
		listSets = null;
		getRecord = null;
		listIdentifiers = null;
		listRecords = null;
	}

	/**
	 * Get the <code>responseDate</code> element.
	 * 
	 * @return the <code>responseDate</code> element
	 */
	public Date getResponseDate() {
		return responseDate;
	}

	/**
	 * Get the <code>request</code> element.
	 * 
	 * @return the <code>request</code> element
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * Get the <code>error</code> elements.
	 * 
	 * @return the <code>error</code> elements
	 */
	public List<Error> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	/**
	 * Get the <code>Identify</code> element.
	 * 
	 * @return the <code>Identify</code> element or <code>null</code> if unspecified
	 */
	public Identify getIdentify() {
		return identify;
	}

	/**
	 * Get the <code>ListMetadataFormats</code> element.
	 * 
	 * @return the <code>ListMetadataFormats</code> element or <code>null</code> if unspecified
	 */
	public ListMetadataFormats getListMetadataFormats() {
		return listMetadataFormats;
	}

	/**
	 * Get the <code>ListSets</code> element.
	 * 
	 * @return the <code>ListSets</code> element or <code>null</code> if unspecified
	 */
	public ListSets getListSets() {
		return listSets;
	}

	/**
	 * Get the <code>GetRecord</code> element.
	 * 
	 * @return the <code>GetRecord</code> element or <code>null</code> if unspecified
	 */
	public GetRecord getGetRecord() {
		return getRecord;
	}

	/**
	 * Get the <code>ListIdentifiers</code> element.
	 * 
	 * @return the <code>ListIdentifiers</code> element or <code>null</code> if unspecified
	 */
	public ListIdentifiers getListIdentifiers() {
		return listIdentifiers;
	}

	/**
	 * Get the <code>ListRecords</code> element.
	 * 
	 * @return the <code>ListRecords</code> element or <code>null</code> if unspecified
	 */
	public ListRecords getListRecords() {
		return listRecords;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof OaiPmhResponse) {
			final OaiPmhResponse oaiPmh = (OaiPmhResponse) object;
			return responseDate.equals(oaiPmh.responseDate) && request.equals(oaiPmh.request) && errors.equals(oaiPmh.errors) && Objects.equals(identify, oaiPmh.identify) && Objects.equals(listMetadataFormats, oaiPmh.listMetadataFormats) && Objects.equals(listSets, oaiPmh.listSets) && Objects.equals(getRecord, oaiPmh.getRecord) && Objects.equals(listIdentifiers, oaiPmh.listIdentifiers) && Objects.equals(listRecords, oaiPmh.listRecords);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(responseDate, request, errors, identify, listMetadataFormats, listSets, getRecord, listIdentifiers, listRecords);
	}
}
