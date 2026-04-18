package io.github.thanospapapetrou.nefeli.friends;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.friends.jaxb.FriendsAdapter;
import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentProvider;

public class FriendsProvider extends XmlContentProvider<Friends> {
    public FriendsProvider() {
        super(FriendsAdapter.class);
    }
}
