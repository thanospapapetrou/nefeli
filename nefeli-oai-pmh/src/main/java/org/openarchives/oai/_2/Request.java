package org.openarchives.oai._2;

import java.net.URI;
import java.net.URL;
import java.time.Instant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.InstantStringAdapter;
import io.github.thanospapapetrou.nefeli.SetSpecAdapter;

/**
 * Define requestType, indicating the protocol request that 
 *       led to the response. Element content is BASE-URL, attributes are arguments 
 *       of protocol request, attribute-values are values of arguments of protocol 
 *       request
 * 
 * &lt;p&gt;Java class for requestType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="requestType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;anyURI"&gt;
 *       &lt;attribute name="verb" type="{http://www.openarchives.org/OAI/2.0/}verbType" /&gt;
 *       &lt;attribute name="identifier" type="{http://www.openarchives.org/OAI/2.0/}identifierType" /&gt;
 *       &lt;attribute name="metadataPrefix" type="{http://www.openarchives.org/OAI/2.0/}metadataPrefixType" /&gt;
 *       &lt;attribute name="from" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType" /&gt;
 *       &lt;attribute name="until" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType" /&gt;
 *       &lt;attribute name="set" type="{http://www.openarchives.org/OAI/2.0/}setSpecType" /&gt;
 *       &lt;attribute name="resumptionToken" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requestType", propOrder = {
    "value"
})
public class Request {
    @XmlValue
    @XmlSchemaType(name = "anyURI")
    private final URL value;
    @XmlAttribute(name = "verb")
    private final Verb verb;
    @XmlAttribute(name = "identifier")
    private final URI identifier;
    @XmlAttribute(name = "metadataPrefix")
    private final String metadataPrefix;
    @XmlAttribute(name = "from")
    @XmlJavaTypeAdapter(InstantStringAdapter.class)
    private final Instant from;
    @XmlAttribute(name = "until")
    @XmlJavaTypeAdapter(InstantStringAdapter.class)
    private final Instant until;
    @XmlAttribute(name = "set")
    @XmlJavaTypeAdapter(SetSpecAdapter.class)
    private final SetSpec set;
    @XmlAttribute(name = "resumptionToken")
    private final String resumptionToken;

    public Request(final URL value, final Verb verb, final URI identifier, final String metadataPrefix,
            final Instant from, final Instant until, final SetSpec set, final String resumptionToken) {
        this.value = value;
        this.verb = verb;
        this.identifier = identifier;
        this.metadataPrefix = metadataPrefix;
        this.from = from;
        this.until = until;
        this.set = set;
        this.resumptionToken = resumptionToken;
    }

    private Request() {
        this(null, null, null, null, null, null, null, null);
    }

    public URL getValue() {
        return value;
    }

    public Verb getVerb() {
        return verb;
    }

    public URI getIdentifier() {
        return identifier;
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public Instant getFrom() {
        return from;
    }

    public Instant getUntil() {
        return until;
    }

    public SetSpec getSet() {
        return set;
    }

    public String getResumptionToken() {
        return resumptionToken;
    }
}
