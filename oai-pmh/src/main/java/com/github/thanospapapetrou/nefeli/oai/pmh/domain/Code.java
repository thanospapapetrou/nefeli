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
	 * the metadata format identified by the value given for the <code>metadataPrefix</code> argument is not supported by the item or by the repository (<code>cannotDisseminateFormat</code>)
	 */
	@XmlEnumValue("cannotDisseminateFormat")
	CANNOT_DISSEMINATE_FORMAT,

	/**
	 * he value of the <code>identifier</code> argument is unknown or illegal in this repository (<code>idDoesNotExist</code>)
	 */
	@XmlEnumValue("idDoesNotExist")
	ID_DOES_NOT_EXIST,
	
	/**
	 * the request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax (<code>badArgument</code>)
	 */
	@XmlEnumValue("badArgument")
	BAD_ARGUMENT,
	
	/**
	 * value of the <code>verb<code> argument is not a legal OAI-PMH verb, the <code>verb</code> argument is missing, or the <code>verb</code argument is repeated (<code>badVerb</code>)
	 */
	@XmlEnumValue("badVerb")
	BAD_VERB,
	
	/**
	 * there are no metadata formats available for the specified item (<code>noMetadataFormats</code>)
	 */
	@XmlEnumValue("noMetadataFormats")
	NO_METADATA_FORMATS,
	
	/**
	 * the combination of the values of the <code>from</code>, <code>until</code>, <code>set</code> and <code>metadataPrefix</code> arguments results in an empty list (<code>noRecordsMatch</code>)
	 */
	@XmlEnumValue("noRecordsMatch")
	NO_RECORDS_MATCH,
	
	/**
	 * the value of the <code>resumptionToken</code> argument is invalid or expired (<code>badResumptionToken</code>)
	 */
	@XmlEnumValue("badResumptionToken")
	BAD_RESUMPTION_TOKEN,
	
	/**
	 * the repository does not support sets (<code>noSetHierarchy</code>)
	 */
	@XmlEnumValue("noSetHierarchy")
	NO_SET_HIERARCHY;
}
