package io.github.thanospapapetrou.nefeli.containers.oai.identifier;

import jakarta.enterprise.inject.spi.CDI;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class OaiIdentifierProvider extends ContainerProvider<OaiIdentifier> {
    public OaiIdentifierProvider() {
        this(CDI.current().select(XmlHelper.class).get(), CDI.current().select(JaxbHelper.class).get());
    }

    private OaiIdentifierProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, OaiIdentifier.class);
    }
}
