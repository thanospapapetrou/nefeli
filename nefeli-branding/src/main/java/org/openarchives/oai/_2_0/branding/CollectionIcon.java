package org.openarchives.oai._2_0.branding;

import java.net.URL;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Java class for collectionIconType complex type&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * 
 * &lt;pre&gt;{&#064;code
 * &lt;complexType name="collectionIconType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * }&lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collectionIconType", propOrder = {
    "url",
    "link",
    "title",
    "width",
    "height"
})
public class CollectionIcon {
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private final URL url;
    @XmlSchemaType(name = "anyURI")
    private final URL link;
    private final String title;
    private final int width;
    private final int height;

    public CollectionIcon(final URL url, final URL link, final String title, final int width, final int height) {
        this.url = url;
        this.link = link;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private CollectionIcon() {
        this(null, null, null, 0, 0);
    }

    public URL getUrl() {
        return url;
    }

    public URL getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
