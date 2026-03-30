package org.openarchives.oai._2;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.internet.InternetAddress;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InstantStringAdapter;
import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InternetAddressAdapter;

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
        "baseUrl",
        "protocolVersion",
        "adminEmails",
        "earliestDatestamp",
        "deletedRecord",
        "granularity",
        "compressions",
        "descriptions"
})
public final class Identify implements OaiPmhBody {
    public static final String COMPRESSION_COMPRESS = "compress";
    public static final String COMPRESSION_DEFLATE = "deflate";
    public static final String COMPRESSION_GZIP = "gzip";
    public static final String COMPRESSION_IDENTITY = "identity"; // TODO is this required?
    public static final String VERSION = "2.0";

    @XmlElement(required = true)
    private final String repositoryName;
    @XmlElement(name = "baseURL", required = true)
    @XmlSchemaType(name = "anyURI")
    private final URL baseUrl;
    @XmlElement(required = true)
    private final String protocolVersion;
    @XmlElement(name = "adminEmail", required = true)
    @XmlJavaTypeAdapter(InternetAddressAdapter.class)
    private final List<InternetAddress> adminEmails;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(InstantStringAdapter.class)
    private final Instant earliestDatestamp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    private final DeletedRecord deletedRecord;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    private final Granularity granularity;
    @XmlElement(name = "compression")
    private final List<String> compressions;
    @XmlElement(name = "description")
    private final List<Description> descriptions;

    public Identify(final String repositoryName, final URL baseUrl, final List<InternetAddress> adminEmails,
            final Instant earliestDatestamp, final DeletedRecord deletedRecord, final Granularity granularity,
            final List<String> compressions, final List<Description> descriptions) {
        this.repositoryName = repositoryName;
        this.baseUrl = baseUrl;
        this.protocolVersion = VERSION;
        this.adminEmails = adminEmails;
        this.earliestDatestamp = earliestDatestamp;
        this.deletedRecord = deletedRecord;
        this.granularity = granularity;
        this.compressions = compressions;
        this.descriptions = descriptions;
    }

    private Identify() {
        this(null, null, new ArrayList<>(), null, null, null, new ArrayList<>(), new ArrayList<>());
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public List<InternetAddress> getAdminEmails() {
        return adminEmails;
    }

    public Instant getEarliestDatestamp() {
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
