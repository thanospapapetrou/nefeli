package org.openarchives.oai._2;

import java.net.URI;
import java.net.URL;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for metadataFormatType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="metadataFormatType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="metadataPrefix" type="{http://www.openarchives.org/OAI/2.0/}metadataPrefixType"/&gt;
 *         &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="metadataNamespace" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataFormatType", propOrder = {
    "metadataPrefix",
    "schema",
    "metadataNamespace"
})
public class MetadataFormat {
    @XmlElement(required = true)
    private final String metadataPrefix;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private final URL schema;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private final URI metadataNamespace;

    public MetadataFormat(final String metadataPrefix, final URL schema, final URI metadataNamespace) {
        this.metadataPrefix = metadataPrefix;
        this.schema = schema;
        this.metadataNamespace = metadataNamespace;
    }

    private MetadataFormat() {
        this(null, null, null);
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public URL getSchema() {
        return schema;
    }

    public URI getMetadataNamespace() {
        return metadataNamespace;
    }
}
