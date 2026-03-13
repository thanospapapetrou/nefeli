package org.openarchives.oai._2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 *
 * &lt;p&gt;Java class for OAI-PMHerrorcodeType&lt;/p&gt;.
 * <p>
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.&lt;/p&gt;
 * &lt;pre&gt;{&#064;code
 * &lt;simpleType name="OAI-PMHerrorcodeType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="cannotDisseminateFormat"/&gt;
 * &lt;enumeration value="idDoesNotExist"/&gt;
 * &lt;enumeration value="badArgument"/&gt;
 * &lt;enumeration value="badVerb"/&gt;
 * &lt;enumeration value="noMetadataFormats"/&gt;
 * &lt;enumeration value="noRecordsMatch"/&gt;
 * &lt;enumeration value="badResumptionToken"/&gt;
 * &lt;enumeration value="noSetHierarchy"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * }&lt;/pre&gt;
 *
 */
@XmlType(name = "OAI-PMHerrorcodeType")
@XmlEnum
public enum OaiPmhErrorCode {

    @XmlEnumValue("cannotDisseminateFormat")
    CANNOT_DISSEMINATE_FORMAT,
    @XmlEnumValue("idDoesNotExist")
    ID_DOES_NOT_EXIST,
    @XmlEnumValue("badArgument")
    BAD_ARGUMENT,
    @XmlEnumValue("badVerb")
    BAD_VERB,
    @XmlEnumValue("noMetadataFormats")
    NO_METADATA_FORMATS,
    @XmlEnumValue("noRecordsMatch")
    NO_RECORDS_MATCH,
    @XmlEnumValue("badResumptionToken")
    BAD_RESUMPTION_TOKEN,
    @XmlEnumValue("noSetHierarchy")
    NO_SET_HIERARCHY;

    @Override
    public String toString() {
        try {
            return getClass().getDeclaredField(name()).getAnnotation(XmlEnumValue.class).value();
        } catch (final NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
}
