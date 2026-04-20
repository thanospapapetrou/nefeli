package io.github.thanospapapetrou.nefeli.containers.friends

import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper
import org.openarchives.oai._2_0.friends.Friends
import spock.lang.Specification

class FriendsProviderTest extends Specification {
    def 'Test constructor'() {
        given:
        final XmlHelper xml = Mock()
        final JaxbHelper jaxb = Mock()
        when:
        final FriendsProvider result = new FriendsProvider(xml, jaxb)
        then:
        result
        result.xml == xml
        result.jaxb == jaxb
        result.container == Friends
    }
}