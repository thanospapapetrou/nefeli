package io.github.thanospapapetrou.nefeli.friends;

import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2_0.friends.Friends;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FriendsAdapter extends XmlAdapter<Element, Friends> {
    private final DocumentBuilder builder;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    @Inject
    public FriendsAdapter(final DocumentBuilder builder, final Marshaller marshaller, final Unmarshaller unmarshaller) {
        this.builder = builder;
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @Override
    public Element marshal(final Friends friends) throws JAXBException {
        final Document document = builder.newDocument();
        marshaller.marshal(friends, document);
        return document.getDocumentElement();
    }

    @Override
    public Friends unmarshal(final Element element) throws JAXBException {
        return unmarshaller.unmarshal(element, Friends.class).getValue();
    }
}
