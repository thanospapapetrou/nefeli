package org.openarchives.oai._2;

import org.w3c.dom.Element;

public class GenericContainer implements DescriptionContainer {
    private final Element element;

    public GenericContainer(final Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
