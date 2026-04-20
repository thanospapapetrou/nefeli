package org.openarchives.oai._2_0.rights;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.openarchives.oai._2.DescriptionContainer;

/**
 * &lt;p&gt;Java class for rightsStatementType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="rightsStatementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="rightsReference"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="rightsDefinition"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;any namespace='##other'/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rights", namespace = Rights.NAMESPACE)
@XmlType(name = "rightsStatementType", propOrder = {
    "rightsReference",
    "rightsDefinition"
})
public class Rights implements DescriptionContainer {
    public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/rights/";
    public static final String PREFIX = "rights";
    public static final String SCHEMA = "https://www.openarchives.org/OAI/2.0/rights.xsd";

    private final RightsReference rightsReference;
    private final RightsDefinition rightsDefinition;

    public Rights(final RightsReference rightsReference) {
        this(rightsReference, null);
    }

    public Rights(final RightsDefinition rightsDefinition) {
        this(null, rightsDefinition);
    }

    private Rights(final RightsReference rightsReference, final RightsDefinition rightsDefinition) {
        this.rightsReference = rightsReference;
        this.rightsDefinition = rightsDefinition;
    }

    private Rights() {
        this(null, null);
    }

    public RightsReference getRightsReference() {
        return rightsReference;
    }

    public RightsDefinition getRightsDefinition() {
        return rightsDefinition;
    }
}
