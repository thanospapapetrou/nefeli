package org.openarchives.oai._2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Metadata must be expressed in XML that complies
 *        with another XML Schema (namespace=#other). Metadata must be 
 *        explicitly qualified in the response.
 * 
 * &lt;p&gt;Java class for metadataType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="metadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;any namespace='##other'/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataType", propOrder = {"metadata"})
public class Metadata {
    @XmlAnyElement(lax = true)
    private final Object metadata;

    public Metadata(final Object metadata) {
        this.metadata = metadata;
    }

    private Metadata() {
        this(null);
    }

    public Object getMetadata() {
        return metadata;
    }
}
