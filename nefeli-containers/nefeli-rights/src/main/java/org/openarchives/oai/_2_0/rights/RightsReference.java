package org.openarchives.oai._2_0.rights;

import java.net.URL;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Java class for anonymous complex type&lt;/p&gt;.
 *
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 *
 * &lt;pre&gt;{&#064;code
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class RightsReference {
    @XmlAttribute(name = "ref", required = true)
    @XmlSchemaType(name = "anyURI")
    private final URL ref;

    public RightsReference(final URL ref) {
        this.ref = ref;
    }

    private RightsReference() {
        this(null);
    }

    public URL getRef() {
        return ref;
    }
}