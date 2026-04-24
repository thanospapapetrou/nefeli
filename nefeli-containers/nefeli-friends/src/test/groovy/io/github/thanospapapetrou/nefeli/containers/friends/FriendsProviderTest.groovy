package io.github.thanospapapetrou.nefeli.containers.friends

import org.openarchives.oai._2_0.friends.Friends
import spock.lang.Specification

class FriendsProviderTest extends Specification {
    def 'Test constructor'() {
        when:
        final FriendsProvider result = new FriendsProvider()
        then:
        result
        result.container == Friends
    }
}
