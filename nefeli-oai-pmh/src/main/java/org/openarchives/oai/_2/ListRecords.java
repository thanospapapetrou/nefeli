package org.openarchives.oai._2;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ListRecordsType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="ListRecordsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="record" type="{http://www.openarchives.org/OAI/2.0/}recordType" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "ListRecordsType", propOrder = {"records", "resumptionToken"})
public class ListRecords implements OaiPmhBody {
    @XmlElement(name = "record", required = true)
    private final List<Record> records;
    private final ResumptionToken resumptionToken;

    public ListRecords(final List<Record> records, final ResumptionToken resumptionToken) {
        this.records = records;
        this.resumptionToken = resumptionToken;
    }

    private ListRecords() {
        this(new ArrayList<>(), null);
    }

    public List<Record> getRecords() {
        return records;
    }

    public ResumptionToken getResumptionToken() {
        return resumptionToken;
    }
}
