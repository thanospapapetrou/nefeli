package io.github.thanospapapetrou.nefeli.containers.friends

import io.github.thanospapapetrou.nefeli.containers.friends.jaxb.FriendsAdapter
import spock.lang.Specification

class FriendsProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final FriendsProvider result = new FriendsProvider()
        then:
        result
        result.@adapter == FriendsAdapter
    }
}