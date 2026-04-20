package io.github.thanospapapetrou.nefeli.containers.branding;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.openarchives.oai._2_0.branding.Branding;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class BrandingProvider extends ContainerProvider<Branding> {
    @Inject
    public BrandingProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Branding.class);
    }
}
