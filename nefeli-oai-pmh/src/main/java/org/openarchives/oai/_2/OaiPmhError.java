package org.openarchives.oai._2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * &lt;p&gt;Java class for OAI-PMHerrorType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="OAI-PMHerrorType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="code" use="required" type="{http://www.openarchives.org/OAI/2.0/}OAI-PMHerrorcodeType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OAI-PMHerrorType", propOrder = {
    "value"
})
public class OaiPmhError {

    @XmlValue
    private final String value;
    @XmlAttribute(name = "code", required = true)
    private final OaiPmhErrorCode code;

    public OaiPmhError(final String value, final OaiPmhErrorCode code) {
        this.value = value;
        this.code = code;
    }

    private OaiPmhError() {
        this(null, null);
    }

    public String getValue() {
        return value;
    }

    public OaiPmhErrorCode getCode() {
        return code;
    }
}
