package io.github.thanospapapetrou.nefeli.friends;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2_0.friends.Friends;
import org.w3c.dom.Element;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContentProvider;

public class FriendsProvider implements ContentProvider<Friends> {
    @Override
    public XmlAdapter<Element, Friends> getAdapter() {
        return CDI.current().select(FriendsAdapter.class).get();
    }
}
