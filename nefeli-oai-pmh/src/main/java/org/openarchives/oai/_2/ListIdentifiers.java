package org.openarchives.oai._2;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ListIdentifiersType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="ListIdentifiersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="header" type="{http://www.openarchives.org/OAI/2.0/}headerType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="resumptionToken" type="{http://www.openarchives.org/OAI/2.0/}resumptionTokenType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListIdentifiersType", propOrder = {
        "headers",
    "resumptionToken"
})
public final class ListIdentifiers implements OaiPmhBody {
    @XmlElement(name = "header", required = true)
    private final List<Header> headers;
    private final ResumptionToken resumptionToken;

    public ListIdentifiers(final List<Header> headers, final ResumptionToken resumptionToken) {
        this.headers = headers;
        this.resumptionToken = resumptionToken;
    }

    private ListIdentifiers() {
        this(new ArrayList<>(), null);
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public ResumptionToken getResumptionToken() {
        return resumptionToken;
    }
}
