package io.github.thanospapapetrou.nefeli.oai.identifier;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2_0.oai_identifier.OaiIdentifier;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContentProvider;

public class OaiIdentifierProvider implements ContentProvider<OaiIdentifier> {
    @Override
    public XmlAdapter<Element, OaiIdentifier> getAdapter() {
        return CDI.current().select(OaiIdentifierAdapter.class).get();
    }
}
