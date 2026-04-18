package io.github.thanospapapetrou.nefeli.containers.friends.jaxb;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerAdapter;

public class FriendsAdapter extends ContainerAdapter<Friends> {
    @Inject
    public FriendsAdapter(final DocumentBuilder builder, @Named("friendsMarshaller") final Marshaller marshaller,
            @Named("friendsUnmarshaller") final Unmarshaller unmarshaller) {
        super(builder, marshaller, unmarshaller, Friends.class);
    }
}
