package io.github.thanospapapetrou.nefeli.containers.oai.identifier.jaxb;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentAdapter;

public class OaiIdentifierAdapter extends XmlContentAdapter<OaiIdentifier> {
    @Inject
    public OaiIdentifierAdapter(final DocumentBuilder builder,
            @Named("oaiIdentifierMarshaller") final Marshaller marshaller,
            @Named("oaiIdentifierUnmarshaller") final Unmarshaller unmarshaller) {
        super(builder, marshaller, unmarshaller, OaiIdentifier.class);
    }
}
