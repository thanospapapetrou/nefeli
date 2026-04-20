package io.github.thanospapapetrou.nefeli.containers.rights;

import jakarta.enterprise.inject.spi.CDI;

import org.openarchives.oai._2_0.rights.Rights;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class RightsProvider extends ContainerProvider<Rights> {
    public RightsProvider() {
        this(CDI.current().select(XmlHelper.class).get(), CDI.current().select(JaxbHelper.class).get());
    }

    private RightsProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Rights.class);
    }
}
