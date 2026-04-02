package io.github.thanospapapetrou.nefeli.oai.identifier;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import jakarta.xml.bind.JAXBException;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;

import io.github.thanospapapetrou.nefeli.oai.pmh.AbstractContainerProvider;

public class OaiIdentifierProvider extends AbstractContainerProvider {
    public OaiIdentifierProvider() throws JAXBException, MalformedURLException, URISyntaxException {
        super(OaiIdentifier.class);
    }
}
