package io.github.thanospapapetrou.nefeli.containers.friends;

import org.openarchives.oai._2_0.friends.Friends;

import io.github.thanospapapetrou.nefeli.containers.friends.jaxb.FriendsAdapter;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class FriendsProvider extends ContainerProvider<Friends> {
    public FriendsProvider() {
        super(FriendsAdapter.class);
    }
}
