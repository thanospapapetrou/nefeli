package org.openarchives.oai._2_0.branding;

import java.net.URI;
import java.net.URL;

import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.branding.jaxb.MediaTypeAdapter;

/**
 * &lt;p&gt;Java class for metadataRenderingType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="metadataRenderingType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;anyURI"&gt;
 *       &lt;attribute name="metadataNamespace" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *       &lt;attribute name="mimeType" use="required" type="{http://www.openarchives.org/OAI/2.0/branding/}mimeType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataRenderingType", propOrder = {
    "value"
})
public class MetadataRendering {
    @XmlValue
    @XmlSchemaType(name = "anyURI")
    private final URL value;
    @XmlAttribute(name = "metadataNamespace", required = true)
    @XmlSchemaType(name = "anyURI")
    private final URI metadataNamespace;
    @XmlAttribute(name = "mimeType", required = true)
    @XmlJavaTypeAdapter(MediaTypeAdapter.class)
    private final MediaType mimeType;

    public MetadataRendering(final URL value, final URI metadataNamespace, final MediaType mimeType) {
        this.value = value;
        this.metadataNamespace = metadataNamespace;
        this.mimeType = mimeType;
    }

    private MetadataRendering() {
        this(null, null, null);
    }

    public URL getValue() {
        return value;
    }

    public URI getMetadataNamespace() {
        return metadataNamespace;
    }

    public MediaType getMimeType() {
        return mimeType;
    }
}
