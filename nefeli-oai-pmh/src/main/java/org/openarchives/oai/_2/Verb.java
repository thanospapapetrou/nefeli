package org.openarchives.oai._2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * &lt;p&gt;Java class for verbType&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * &lt;pre&gt;{&#064;code
 * &lt;simpleType name="verbType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Identify"/&gt;
 *     &lt;enumeration value="ListMetadataFormats"/&gt;
 *     &lt;enumeration value="ListSets"/&gt;
 *     &lt;enumeration value="GetRecord"/&gt;
 *     &lt;enumeration value="ListIdentifiers"/&gt;
 *     &lt;enumeration value="ListRecords"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * }&lt;/pre&gt;
 * 
 */
@XmlType(name = "verbType")
@XmlEnum
public enum Verb {
    @XmlEnumValue("Identify")
    IDENTIFY,
    @XmlEnumValue("ListMetadataFormats")
    LIST_METADATA_FORMATS,
    @XmlEnumValue("ListSets")
    LIST_SETS,
    @XmlEnumValue("GetRecord")
    GET_RECORD,
    @XmlEnumValue("ListIdentifiers")
    LIST_IDENTIFIERS,
    @XmlEnumValue("ListRecords")
    LIST_RECORDS;

    @Override
    public String toString() {
        try {
            return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
        } catch (final NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
}
