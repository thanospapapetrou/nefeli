package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enumeration defining <code>code</code> attribute of <code>error</code> OAI-PMH element.
 * 
 * @author thanos
 */
@XmlEnum
@XmlType(name = "OAI-PMHerrorcodeType")
public enum Code {
	/**
	 * <code>cannotDisseminateFormat</code>
	 */
	@XmlEnumValue("cannotDisseminateFormat")
	CANNOT_DISSEMINATE_FORMAT,

	/**
	 * <code>idDoesNotExist</code>
	 */
	@XmlEnumValue("idDoesNotExist")
	ID_DOES_NOT_EXIST,
	
	/**
	 * <code>badArgument</code>
	 */
	@XmlEnumValue("badArgument")
	BAD_ARGUMENT,
	
	/**
	 * <code>badVerb</code>
	 */
	@XmlEnumValue("badVerb")
	BAD_VERB,
	
	/**
	 * <code>noMetadataFormats</code>
	 */
	@XmlEnumValue("noMetadataFormats")
	NO_METADATA_FORMATS,
	
	/**
	 * <code>noRecordsMatch</code>
	 */
	@XmlEnumValue("noRecordsMatch")
	NO_RECORDS_MATCH,
	
	/**
	 * <code>badResumptionToken</code>
	 */
	@XmlEnumValue("badResumptionToken")
	BAD_RESUMPTION_TOKEN,
	
	/**
	 * <code>noSetHierarchy</code>
	 */
	@XmlEnumValue("noSetHierarchy")
	NO_SET_HIERARCHY;
}
