package io.github.thanospapapetrou.nefeli.friends;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import jakarta.xml.bind.JAXBException;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.oai.pmh.AbstractContainerProvider;

public class FriendsProvider extends AbstractContainerProvider {
    public FriendsProvider() throws JAXBException, MalformedURLException, URISyntaxException {
        super(Friends.class);
    }
}
