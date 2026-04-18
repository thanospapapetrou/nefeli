package edu.vt.dlib.oai.oai.metadata.toolkit;

import java.net.URL;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

import org.openarchives.oai._2.DescriptionContent;

/**
 * &lt;p&gt;Java class for toolkitType complex type&lt;/p&gt;.
 * <p>
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * <p>
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="toolkitType"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * &lt;element name="author" type="{http://oai.dlib.vt.edu/OAI/metadata/toolkit}authorType" minOccurs="0"/&gt;
 * &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 * &lt;element name="toolkitIcon" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 * &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 * &lt;/sequence&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "toolkit", namespace = Toolkit.NAMESPACE)
@XmlType(name = "toolkitType", propOrder = {
        "title",
        "author",
        "version",
        "toolkitIcon",
        "url"
})
public class Toolkit implements DescriptionContent {
    public static final String NAMESPACE = "http://oai.dlib.vt.edu/OAI/metadata/toolkit";
    public static final String PREFIX = "toolkit";
    public static final String SCHEMA = "http://oai.dlib.vt.edu/OAI/metadata/toolkit.xsd";

    private final String title;
    private final Author author;
    private final String version;
    @XmlSchemaType(name = "anyURI")
    private final URL toolkitIcon;
    @XmlElement(name = "URL")
    @XmlSchemaType(name = "anyURI")
    private final URL url;

    public Toolkit(final String title, final Author author, final String version, final URL toolkitIcon,
            final URL url) {
        this.title = title;
        this.author = author;
        this.version = version;
        this.toolkitIcon = toolkitIcon;
        this.url = url;
    }

    private Toolkit() {
        this(null, null, null, null, null);
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public URL getToolkitIcon() {
        return toolkitIcon;
    }

    public URL getURL() {
        return url;
    }
}
