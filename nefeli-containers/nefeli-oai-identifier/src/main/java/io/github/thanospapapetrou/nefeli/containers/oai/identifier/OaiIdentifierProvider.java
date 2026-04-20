package io.github.thanospapapetrou.nefeli.containers.oai.identifier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class OaiIdentifierProvider extends ContainerProvider<OaiIdentifier> {
    @Inject
    public OaiIdentifierProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, OaiIdentifier.class);
    }
}
