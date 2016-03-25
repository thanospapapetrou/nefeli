/**
 * This package defines JAXB bindings for OAI-PMH 2.0.
 * 
 * @author thanos
 * @see <a href="https://www.openarchives.org/OAI/openarchivesprotocol.html">The Open Archives Initiative Protocol for Metadata Harvesting</a>
 */
@XmlSchema(namespace = OaiPmh.NAMESPACE, xmlns = {
	@XmlNs(namespaceURI = OaiPmh.NAMESPACE, prefix = "oai")
}, location = "http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd", elementFormDefault = XmlNsForm.QUALIFIED)
package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
