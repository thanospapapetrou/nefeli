package org.openarchives.oai._2;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.jaxb.adapters.InstantStringAdapter;

/**
 * A header has a unique identifier, a datestamp,
 *         and setSpec(s) in case the item from which
 *         the record is disseminated belongs to set(s).
 *         the header can carry a deleted status indicating
 *         that the record is deleted.
 * 
 * &lt;p&gt;Java class for headerType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="headerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identifier" type="{http://www.openarchives.org/OAI/2.0/}identifierType"/&gt;
 *         &lt;element name="datestamp" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType"/&gt;
 *         &lt;element name="setSpec" type="{http://www.openarchives.org/OAI/2.0/}setSpecType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="status" type="{http://www.openarchives.org/OAI/2.0/}statusType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerType", propOrder = {"identifier", "datestamp", "setSpecs"})
public class Header {
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private final URI identifier;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(InstantStringAdapter.class)
    private final Instant datestamp;
    @XmlElement(name = "setSpec")
    private final List<String> setSpecs;
    @XmlAttribute(name = "status")
    private final Status status;

    public Header(final URI identifier, final Instant datestamp, final List<String> setSpecs, final boolean deleted) {
        this.identifier = identifier;
        this.datestamp = datestamp;
        this.setSpecs = setSpecs;
        this.status = deleted ? Status.DELETED : null;
    }

    private Header() {
        this(null, null, new ArrayList<>(), false);
    }

    public URI getIdentifier() {
        return identifier;
    }

    public Instant getDatestamp() {
        return datestamp;
    }

    public List<String> getSetSpecs() {
        return setSpecs;
    }

    public boolean isDeleted() {
        return status == Status.DELETED;
    }
}
