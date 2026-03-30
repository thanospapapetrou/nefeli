package org.openarchives.oai._2;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ListMetadataFormatsType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="ListMetadataFormatsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="metadataFormat" type="{http://www.openarchives.org/OAI/2.0/}metadataFormatType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListMetadataFormatsType", propOrder = {"metadataFormats"})
public final class ListMetadataFormats implements OaiPmhBody {

    @XmlElement(name = "metadataFormat", required = true)
    private final List<MetadataFormat> metadataFormats;

    public ListMetadataFormats(final List<MetadataFormat> metadataFormats) {
        this.metadataFormats = metadataFormats;
    }

    private ListMetadataFormats() {
        this(new ArrayList<>());
    }

    public List<MetadataFormat> getMetadataFormats() {
        return metadataFormats;
    }
}
