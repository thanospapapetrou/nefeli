package io.github.thanospapapetrou.nefeli.containers.branding;

import org.openarchives.oai._2_0.branding.Branding;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class BrandingProvider extends ContainerProvider<Branding> {
    public BrandingProvider() {
        super(Branding.class);
    }
}
