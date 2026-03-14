package org.openarchives.oai._2;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for setType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="setType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="setSpec" type="{http://www.openarchives.org/OAI/2.0/}setSpecType"/&gt;
 *         &lt;element name="setName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="setDescription" type="{http://www.openarchives.org/OAI/2.0/}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setType", propOrder = {"setSpec", "setName", "setDescriptions"})
public class OaiPmhSet {
    @XmlElement(required = true)
    private final String setSpec; // TODO set spec
    @XmlElement(required = true)
    private final String setName;
    @XmlElement(name = "setDescription")
    private final List<Description> setDescriptions;

    public OaiPmhSet(final String setSpec, final String setName, final List<Description> setDescriptions) {
        this.setSpec = setSpec;
        this.setName = setName;
        this.setDescriptions = setDescriptions;
    }

    private OaiPmhSet() {
        this(null, null, new ArrayList<>());
    }

    public String getSetSpec() {
        return setSpec;
    }

    public String getSetName() {
        return setName;
    }

    public List<Description> getSetDescriptions() {
        return setDescriptions;
    }

}
