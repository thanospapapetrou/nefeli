package org.openarchives.oai._2_0.friends;

import java.net.URL;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

// TODO https://www.openarchives.org/OAI/2.0/guidelines-friends.htm

/**
 * &lt;p&gt;Java class for friendsType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="friendsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="baseURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "friends", namespace = Friends.NAMESPACE)
@XmlType(name = "friendsType", propOrder = {"baseUrls"})
public class Friends {
    public static final String NAMESPACE = "http://www.openarchives.org/OAI/2.0/friends/";
    public static final String PREFIX = "friends";
    public static final String SCHEMA = "https://www.openarchives.org/OAI/2.0/friends.xsd";

    @XmlElement(name = "baseURL")
    @XmlSchemaType(name = "anyURI")
    private final List<URL> baseUrls;

    public Friends(final List<URL> baseUrls) {
        this.baseUrls = baseUrls;
    }

    public List<URL> getBaseUrls() {
        return baseUrls;
    }

}
