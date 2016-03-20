//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.09 at 10:50:49 PM EET 
//


package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OAI-PMHtype complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OAI-PMHtype">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="request" type="{http://www.openarchives.org/OAI/2.0/}requestType"/>
 *         &lt;choice>
 *           &lt;element name="error" type="{http://www.openarchives.org/OAI/2.0/}OAI-PMHerrorType" maxOccurs="unbounded"/>
 *           &lt;element name="Identify" type="{http://www.openarchives.org/OAI/2.0/}IdentifyType"/>
 *           &lt;element name="ListMetadataFormats" type="{http://www.openarchives.org/OAI/2.0/}ListMetadataFormatsType"/>
 *           &lt;element name="ListSets" type="{http://www.openarchives.org/OAI/2.0/}ListSetsType"/>
 *           &lt;element name="GetRecord" type="{http://www.openarchives.org/OAI/2.0/}GetRecordType"/>
 *           &lt;element name="ListIdentifiers" type="{http://www.openarchives.org/OAI/2.0/}ListIdentifiersType"/>
 *           &lt;element name="ListRecords" type="{http://www.openarchives.org/OAI/2.0/}ListRecordsType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OAI-PMHtype", propOrder = {
    "responseDate",
    "request",
    "error",
    "identify",
    "listMetadataFormats",
    "listSets",
    "getRecord",
    "listIdentifiers",
    "listRecords"
})
public class OaiPmh {
	public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/";
	public static final String SCHEMA = "http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";
	static final String IDENTIFIER_TYPE = "identifierType";
	static final String UTC_DATETIME_TYPE = "UTCdatetimeType";

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseDate;
    @XmlElement(required = true)
    protected Request request;
    protected List<Error> error;
    @XmlElement(name = "Identify")
    protected Identify identify;
    @XmlElement(name = "ListMetadataFormats")
    protected ListMetadataFormats listMetadataFormats;
    @XmlElement(name = "ListSets")
    protected ListSets listSets;
    @XmlElement(name = "GetRecord")
    protected GetRecord getRecord;
    @XmlElement(name = "ListIdentifiers")
    protected ListIdentifiers listIdentifiers;
    @XmlElement(name = "ListRecords")
    protected ListRecords listRecords;

    /**
     * Gets the value of the responseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseDate() {
        return responseDate;
    }

    /**
     * Sets the value of the responseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseDate(XMLGregorianCalendar value) {
        this.responseDate = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link Request }
     *     
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link Request }
     *     
     */
    public void setRequest(Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the error property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Error }
     * 
     * 
     */
    public List<Error> getError() {
        if (error == null) {
            error = new ArrayList<Error>();
        }
        return this.error;
    }

    /**
     * Gets the value of the identify property.
     * 
     * @return
     *     possible object is
     *     {@link Identify }
     *     
     */
    public Identify getIdentify() {
        return identify;
    }

    /**
     * Sets the value of the identify property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identify }
     *     
     */
    public void setIdentify(Identify value) {
        this.identify = value;
    }

    /**
     * Gets the value of the listMetadataFormats property.
     * 
     * @return
     *     possible object is
     *     {@link ListMetadataFormats }
     *     
     */
    public ListMetadataFormats getListMetadataFormats() {
        return listMetadataFormats;
    }

    /**
     * Sets the value of the listMetadataFormats property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMetadataFormats }
     *     
     */
    public void setListMetadataFormats(ListMetadataFormats value) {
        this.listMetadataFormats = value;
    }

    /**
     * Gets the value of the listSets property.
     * 
     * @return
     *     possible object is
     *     {@link ListSets }
     *     
     */
    public ListSets getListSets() {
        return listSets;
    }

    /**
     * Sets the value of the listSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSets }
     *     
     */
    public void setListSets(ListSets value) {
        this.listSets = value;
    }

    /**
     * Gets the value of the getRecord property.
     * 
     * @return
     *     possible object is
     *     {@link GetRecord }
     *     
     */
    public GetRecord getGetRecord() {
        return getRecord;
    }

    /**
     * Sets the value of the getRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetRecord }
     *     
     */
    public void setGetRecord(GetRecord value) {
        this.getRecord = value;
    }

    /**
     * Gets the value of the listIdentifiers property.
     * 
     * @return
     *     possible object is
     *     {@link ListIdentifiers }
     *     
     */
    public ListIdentifiers getListIdentifiers() {
        return listIdentifiers;
    }

    /**
     * Sets the value of the listIdentifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListIdentifiers }
     *     
     */
    public void setListIdentifiers(ListIdentifiers value) {
        this.listIdentifiers = value;
    }

    /**
     * Gets the value of the listRecords property.
     * 
     * @return
     *     possible object is
     *     {@link ListRecords }
     *     
     */
    public ListRecords getListRecords() {
        return listRecords;
    }

    /**
     * Sets the value of the listRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRecords }
     *     
     */
    public void setListRecords(ListRecords value) {
        this.listRecords = value;
    }

}
