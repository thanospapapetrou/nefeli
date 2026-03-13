package org.openarchives.oai._2;

import java.net.URL;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for IdentifyType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="IdentifyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="repositoryName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="baseURL" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="protocolVersion" type="{http://www.openarchives.org/OAI/2.0/}protocolVersionType"/&gt;
 *         &lt;element name="adminEmail" type="{http://www.openarchives.org/OAI/2.0/}emailType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="earliestDatestamp" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType"/&gt;
 *         &lt;element name="deletedRecord" type="{http://www.openarchives.org/OAI/2.0/}deletedRecordType"/&gt;
 *         &lt;element name="granularity" type="{http://www.openarchives.org/OAI/2.0/}granularityType"/&gt;
 *         &lt;element name="compression" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.openarchives.org/OAI/2.0/}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifyType", propOrder = {
    "repositoryName",
    "baseURL",
        "protocolVersion", "adminEmails",
    "earliestDatestamp",
    "deletedRecord",
        "granularity", "compressions", "descriptions"
})
public class Identify implements OaiPmhBody {
    @XmlElement(required = true)
    private final String repositoryName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private final URL baseURL;
    @XmlElement(required = true)
    private final String protocolVersion;
    @XmlElement(required = true)
    private final List<String> adminEmails; // TODO email
    @XmlElement(required = true)
    private final String earliestDatestamp; // TODO instant
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    private final DeletedRecord deletedRecord;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    private final Granularity granularity;
    private final List<String> compressions; // TODO gzip compress deflate identity
    private final List<Description> descriptions;

    public Identify(final String repositoryName, final URL baseURL, final List<String> adminEmails,
            final String earliestDatestamp, final DeletedRecord deletedRecord, final Granularity granularity,
            final List<String> compressions, final List<Description> descriptions) {
        this.repositoryName = repositoryName;
        this.baseURL = baseURL;
        this.protocolVersion = OaiPmhResponse.VERSION;
        this.adminEmails = adminEmails;
        this.earliestDatestamp = earliestDatestamp;
        this.deletedRecord = deletedRecord;
        this.granularity = granularity;
        this.compressions = compressions;
        this.descriptions = descriptions;
    }

    private Identify() {
        this(null, null, List.of(), null, null, null, List.of(), List.of());
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public URL getBaseURL() {
        return baseURL;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public List<String> getAdminEmails() {
        return adminEmails;
    }

    public String getEarliestDatestamp() {
        return earliestDatestamp;
    }

    public DeletedRecord getDeletedRecord() {
        return deletedRecord;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public List<String> getCompressions() {
        return compressions;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }
}
