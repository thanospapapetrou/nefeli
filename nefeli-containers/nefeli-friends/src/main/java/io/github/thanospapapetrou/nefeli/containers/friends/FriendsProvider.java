package io.github.thanospapapetrou.nefeli.containers.friends;

import jakarta.enterprise.inject.spi.CDI;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class FriendsProvider extends ContainerProvider<Friends> {
    public FriendsProvider() {
        this(CDI.current().select(XmlHelper.class).get(), CDI.current().select(JaxbHelper.class).get());
    }

    private FriendsProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Friends.class);
    }
}
