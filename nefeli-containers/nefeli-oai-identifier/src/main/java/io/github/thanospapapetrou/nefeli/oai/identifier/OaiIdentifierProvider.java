package io.github.thanospapapetrou.nefeli.oai.identifier;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.oai.identifier.jaxb.OaiIdentifierAdapter;
import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentProvider;

public class OaiIdentifierProvider extends XmlContentProvider<OaiIdentifier> {
    public OaiIdentifierProvider() {
        super(OaiIdentifierAdapter.class);
    }
}
