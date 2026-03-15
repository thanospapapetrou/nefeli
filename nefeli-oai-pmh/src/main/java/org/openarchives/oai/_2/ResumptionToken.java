package org.openarchives.oai._2;

import java.math.BigInteger;
import java.time.Instant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.jaxb.adapters.InstantCalendarAdapter;

/**
 * A resumptionToken may have 3 optional attributes
 *        and can be used in ListSets, ListIdentifiers, ListRecords
 *        responses.
 * 
 * &lt;p&gt;Java class for resumptionTokenType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="resumptionTokenType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="completeListSize" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="cursor" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumptionTokenType", propOrder = {"value"})
public class ResumptionToken {
    @XmlValue
    private final String value;
    @XmlAttribute(name = "expirationDate")
    @XmlJavaTypeAdapter(InstantCalendarAdapter.class)
    @XmlSchemaType(name = "dateTime")
    private final Instant expirationDate;
    @XmlAttribute(name = "completeListSize")
    @XmlSchemaType(name = "positiveInteger")
    private final BigInteger completeListSize;
    @XmlAttribute(name = "cursor")
    @XmlSchemaType(name = "nonNegativeInteger")
    private final BigInteger cursor;

    public ResumptionToken(final String value, final Instant expirationDate,
            final BigInteger completeListSize, final BigInteger cursor) {
        this.value = value;
        this.expirationDate = expirationDate;
        this.completeListSize = completeListSize;
        this.cursor = cursor;
    }

    private ResumptionToken() {
        this(null, null, null, null);
    }

    public String getValue() {
        return value;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public BigInteger getCompleteListSize() {
        return completeListSize;
    }

    public BigInteger getCursor() {
        return cursor;
    }
}
