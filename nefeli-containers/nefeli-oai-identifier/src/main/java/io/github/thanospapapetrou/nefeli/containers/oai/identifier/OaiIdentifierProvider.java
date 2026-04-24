package io.github.thanospapapetrou.nefeli.containers.oai.identifier;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class OaiIdentifierProvider extends ContainerProvider<OaiIdentifier> {
    public OaiIdentifierProvider() {
        super(OaiIdentifier.class);
    }
}
