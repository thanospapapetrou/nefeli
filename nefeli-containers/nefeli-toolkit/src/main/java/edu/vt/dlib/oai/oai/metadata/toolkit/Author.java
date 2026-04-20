package edu.vt.dlib.oai.oai.metadata.toolkit;

import jakarta.mail.internet.InternetAddress;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.thanospapapetrou.nefeli.oai.pmh.jaxb.InternetAddressAdapter;

/**
 * &lt;p&gt;Java class for authorType complex type&lt;/p&gt;.
 *
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 *
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="authorType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="institution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorType", propOrder = {
        "name",
        "email",
        "institution"
})
public class Author {
    private final String name;
    @XmlJavaTypeAdapter(InternetAddressAdapter.class)
    private final InternetAddress email;
    private final String institution;

    public Author(final String name, final InternetAddress email, final String institution) {
        this.name = name;
        this.email = email;
        this.institution = institution;
    }

    private Author() {
        this(null, null, null);
    }

    public String getName() {
        return name;
    }

    public InternetAddress getEmail() {
        return email;
    }

    public String getInstitution() {
        return institution;
    }
}
