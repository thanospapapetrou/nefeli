package org.openarchives.oai._2_0.oai_identifier;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.openarchives.oai._2.DescriptionContent;

/**
 * &lt;p&gt;Java class for oai-identifierType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="oai-identifierType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="repositoryIdentifier" type="{http://www.openarchives.org/OAI/2.0/oai-identifier}repositoryIdentifierType"/&gt;
 *         &lt;element name="delimiter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sampleIdentifier" type="{http://www.openarchives.org/OAI/2.0/oai-identifier}sampleIdentifierType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "oai-identifier", namespace = OaiIdentifier.NAMESPACE)
@XmlType(name = "oai-identifierType", propOrder = {
    "scheme",
    "repositoryIdentifier",
    "delimiter",
    "sampleIdentifier"
})
public class OaiIdentifier implements DescriptionContent {
    public static final String DELIMITER = ":";
    public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/oai-identifier/";
    public static final String PREFIX = "oai-identifier";
    public static final String SCHEMA = "https://www.openarchives.org/OAI/2.0/oai-identifier.xsd";

// TODO https://www.openarchives.org/OAI/2.0/guidelines-oai-identifier.htm

    @XmlElement(required = true)
    private final String scheme;
    @XmlElement(required = true)
    private final String repositoryIdentifier;
    @XmlElement(required = true)
    private final String delimiter;
    @XmlElement(required = true)
    private final String sampleIdentifier;

    public OaiIdentifier(final String scheme, final String repositoryIdentifier, final String sampleIdentifier) {
        this.scheme = scheme;
        this.repositoryIdentifier = repositoryIdentifier;
        this.delimiter = DELIMITER;
        this.sampleIdentifier = sampleIdentifier;
    }

    public String getScheme() {
        return scheme;
    }

    public String getRepositoryIdentifier() {
        return repositoryIdentifier;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getSampleIdentifier() {
        return sampleIdentifier;
    }
}
