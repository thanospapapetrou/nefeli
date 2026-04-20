package io.github.thanospapapetrou.nefeli.containers.friends;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class FriendsProvider extends ContainerProvider<Friends> {
    @Inject
    public FriendsProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Friends.class);
    }
}
