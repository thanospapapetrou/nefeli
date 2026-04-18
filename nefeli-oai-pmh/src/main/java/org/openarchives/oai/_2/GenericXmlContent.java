package org.openarchives.oai._2;

import org.w3c.dom.Element;

public class GenericXmlContent implements DescriptionContent {
    private final Element element;

    public GenericXmlContent(final Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
