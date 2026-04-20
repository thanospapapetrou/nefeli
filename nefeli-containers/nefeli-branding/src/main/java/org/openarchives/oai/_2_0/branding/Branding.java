package org.openarchives.oai._2_0.branding;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.openarchives.oai._2.DescriptionContainer;

/**
 * &lt;p&gt;Java class for anonymous complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="collectionIcon" type="{http://www.openarchives.org/OAI/2.0/branding/}collectionIconType" minOccurs="0"/&gt;
 *         &lt;element name="metadataRendering" type="{http://www.openarchives.org/OAI/2.0/branding/}metadataRenderingType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "branding", namespace = Branding.NAMESPACE)
@XmlType(name = "", propOrder = {
    "collectionIcon",
    "metadataRendering"
})
public class Branding implements DescriptionContainer {
    public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/branding/";
    public static final String PREFIX = "branding";
    public static final String SCHEMA = "https://www.openarchives.org/OAI/2.0/branding.xsd";

    private final CollectionIcon collectionIcon;
    private final List<MetadataRendering> metadataRendering;

    public Branding(final CollectionIcon collectionIcon, final List<MetadataRendering> metadataRendering) {
        this.collectionIcon = collectionIcon;
        this.metadataRendering = metadataRendering;
    }

    private Branding() {
        this(null, null);
    }

    public CollectionIcon getCollectionIcon() {
        return collectionIcon;
    }

    public List<MetadataRendering> getMetadataRendering() {
        return metadataRendering;
    }
}
