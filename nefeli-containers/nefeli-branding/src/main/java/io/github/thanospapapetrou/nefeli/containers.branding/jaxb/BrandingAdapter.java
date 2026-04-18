package io.github.thanospapapetrou.nefeli.branding.jaxb;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2_0.branding.Branding;

import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentAdapter;

public class BrandingAdapter extends XmlContentAdapter<Branding> {
    @Inject
    public BrandingAdapter(final DocumentBuilder builder, @Named("brandingMarshaller") final Marshaller marshaller,
            @Named("brandingUnmarshaller") final Unmarshaller unmarshaller) {
        super(builder, marshaller, unmarshaller, Branding.class);
    }
}
