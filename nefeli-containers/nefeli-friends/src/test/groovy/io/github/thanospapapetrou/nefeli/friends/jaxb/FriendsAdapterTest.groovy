package io.github.thanospapapetrou.nefeli.friends.jaxb

import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import org.openarchives.oai._2_0.friends.Friends
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder

class FriendsAdapterTest extends Specification {
    def 'Test constructor'() {
        given:
        final DocumentBuilder builder = Mock()
        final Marshaller marshaller = Mock()
        final Unmarshaller unmarshaller = Mock()
        when:
        final FriendsAdapter result = new FriendsAdapter(builder, marshaller, unmarshaller)
        then:
        result
        result.builder == builder
        result.marshaller == marshaller
        result.unmarshaller == unmarshaller
        result.clazz == Friends
    }
}