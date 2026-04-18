package io.github.thanospapapetrou.nefeli.branding;

import org.openarchives.oai._2_0.branding.Branding;

import io.github.thanospapapetrou.nefeli.branding.jaxb.BrandingAdapter;
import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentProvider;

public class BrandingProvider extends XmlContentProvider<Branding> {
    public BrandingProvider() {
        super(BrandingAdapter.class);
    }
}
