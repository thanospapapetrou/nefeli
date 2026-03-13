package org.openarchives.oai._2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * &lt;p&gt;Java class for statusType&lt;/p&gt;.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * &lt;pre&gt;{&#064;code
 * &lt;simpleType name="statusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="deleted"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * }&lt;/pre&gt;
 * 
 */
@XmlType(name = "statusType")
@XmlEnum
public enum Status {
    @XmlEnumValue("deleted")
    DELETED;

    @Override
    public String toString() {
        try {
            return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
        } catch (final NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
}
