package org.openarchives.oai._2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.InstantAdapter;

/**
 * &lt;p&gt;Java class for OAI-PMHtype complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="OAI-PMHtype"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="request" type="{http://www.openarchives.org/OAI/2.0/}requestType"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="error" type="{http://www.openarchives.org/OAI/2.0/}OAI-PMHerrorType" maxOccurs="unbounded"/&gt;
 *           &lt;element name="Identify" type="{http://www.openarchives.org/OAI/2.0/}IdentifyType"/&gt;
 *           &lt;element name="ListMetadataFormats" type="{http://www.openarchives.org/OAI/2.0/}ListMetadataFormatsType"/&gt;
 *           &lt;element name="ListSets" type="{http://www.openarchives.org/OAI/2.0/}ListSetsType"/&gt;
 *           &lt;element name="GetRecord" type="{http://www.openarchives.org/OAI/2.0/}GetRecordType"/&gt;
 *           &lt;element name="ListIdentifiers" type="{http://www.openarchives.org/OAI/2.0/}ListIdentifiersType"/&gt;
 *           &lt;element name="ListRecords" type="{http://www.openarchives.org/OAI/2.0/}ListRecordsType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OAI-PMH", namespace = OaiPmhResponse.NAMESPACE)
@XmlType(name = "OAI-PMHtype", propOrder = {
    "responseDate",
    "request",
        "errors",
    "identify",
    "listMetadataFormats",
    "listSets",
    "getRecord",
    "listIdentifiers",
    "listRecords"
})
public class OaiPmhResponse<T extends OaiPmhBody> {
    public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/";
    public static final String PREFIX = "oai";
    public static final String SCHEMA = "https://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(InstantAdapter.class)
    private final Instant responseDate;
    @XmlElement(required = true)
    private final Request request;
    @XmlElement(name = "error")
    private final List<OaiPmhError> errors;
    @XmlElement(name = "Identify")
    private final Identify identify;
    @XmlElement(name = "ListMetadataFormats")
    private final ListMetadataFormats listMetadataFormats;
    @XmlElement(name = "ListSets")
    private final ListSets listSets;
    @XmlElement(name = "GetRecord")
    private final GetRecord getRecord;
    @XmlElement(name = "ListIdentifiers")
    private final ListIdentifiers listIdentifiers;
    @XmlElement(name = "ListRecords")
    private final ListRecords listRecords;

    public OaiPmhResponse(final Instant responseDate, final Request request, final Identify identify) {
        this(responseDate, request, new ArrayList<>(), identify, null, null, null, null, null);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request,
            final ListMetadataFormats listMetadataFormats) {
        this(responseDate, request, new ArrayList<>(), null, listMetadataFormats, null, null, null, null);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request, final ListSets listSets) {
        this(responseDate, request, new ArrayList<>(), null, null, listSets, null, null, null);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request, final GetRecord getRecord) {
        this(responseDate, request, new ArrayList<>(), null, null, null, getRecord, null, null);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request, final ListIdentifiers listIdentifiers) {
        this(responseDate, request, new ArrayList<>(), null, null, null, null, listIdentifiers, null);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request, final ListRecords listRecords) {
        this(responseDate, request, new ArrayList<>(), null, null, null, null, null, listRecords);
    }

    public OaiPmhResponse(final Instant responseDate, final Request request, final List<OaiPmhError> errors) {
        this(responseDate, request, errors, null, null, null, null, null, null);
    }

    private OaiPmhResponse() {
        this(null, null, new ArrayList<>(), null, null, null, null, null, null);
    }

    private OaiPmhResponse(final Instant responseDate, final Request request,
            final List<OaiPmhError> errors, final Identify identify, final ListMetadataFormats listMetadataFormats,
            final ListSets listSets, final GetRecord getRecord, final ListIdentifiers listIdentifiers,
            final ListRecords listRecords) {
        this.responseDate = responseDate;
        this.request = request;
        this.errors = errors;
        this.identify = identify;
        this.listMetadataFormats = listMetadataFormats;
        this.listSets = listSets;
        this.getRecord = getRecord;
        this.listIdentifiers = listIdentifiers;
        this.listRecords = listRecords;
    }

    public Instant getResponseDate() {
        return responseDate;
    }

    public Request getRequest() {
        return request;
    }

    public List<OaiPmhError> getErrors() {
        return errors;
    }

    public T getBody() {
        return (T) switch (request.getVerb()) {
            case IDENTIFY -> identify;
            case LIST_METADATA_FORMATS -> listMetadataFormats;
            case LIST_SETS -> listSets;
            case GET_RECORD -> getRecord;
            case LIST_IDENTIFIERS -> listIdentifiers;
            case LIST_RECORDS -> listRecords;
        };
    }
}
