package org.openarchives.oai._2;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * A record has a header, a metadata part, and
 *         an optional about container
 * 
 * &lt;p&gt;Java class for recordType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="recordType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="header" type="{http://www.openarchives.org/OAI/2.0/}headerType"/&gt;
 *         &lt;element name="metadata" type="{http://www.openarchives.org/OAI/2.0/}metadataType" minOccurs="0"/&gt;
 *         &lt;element name="about" type="{http://www.openarchives.org/OAI/2.0/}aboutType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", propOrder = {
    "header", "metadata", "abouts"
})
public class Record {
    @XmlElement(required = true)
    private final Header header;
    private final Metadata metadata;
    @XmlElement(name = "about")
    private final List<About> abouts;

    public Record(final Header header, final Metadata metadata, final List<About> abouts) {
        this.header = header;
        this.metadata = metadata;
        this.abouts = abouts;
    }

    private Record() {
        this(null, null, new ArrayList<>());
    }

    public Header getHeader() {
        return header;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public List<About> getAbouts() {
        return abouts;
    }
}
