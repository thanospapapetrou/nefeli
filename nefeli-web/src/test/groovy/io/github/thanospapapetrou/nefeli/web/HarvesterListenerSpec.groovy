package io.github.thanospapapetrou.nefeli.web

import io.github.thanospapapetrou.nefeli.harvester.Harvester
import jakarta.servlet.ServletContextEvent
import spock.lang.Specification

class HarvesterListenerSpec extends Specification {
    private HarvesterListener listener

    def setup() {
        listener = new HarvesterListener(Mock(Harvester))
    }

    def 'Test context initialized'() {
        given:
        final ServletContextEvent event = Mock()
        when:
        listener.contextInitialized(event)
        then:
        1 * listener.harvester.run()
    }
}