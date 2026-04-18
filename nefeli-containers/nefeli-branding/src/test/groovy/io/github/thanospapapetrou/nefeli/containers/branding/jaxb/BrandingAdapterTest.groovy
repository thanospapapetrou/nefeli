package io.github.thanospapapetrou.nefeli.containers.branding.jaxb

import io.github.thanospapapetrou.nefeli.branding.jaxb.BrandingAdapter
import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.Unmarshaller
import org.openarchives.oai._2_0.branding.Branding
import spock.lang.Specification

import javax.xml.parsers.DocumentBuilder

class BrandingAdapterTest extends Specification {
    def 'Test constructor'() {
        given:
        final DocumentBuilder builder = Mock()
        final Marshaller marshaller = Mock()
        final Unmarshaller unmarshaller = Mock()
        when:
        final BrandingAdapter result = new BrandingAdapter(builder, marshaller, unmarshaller)
        then:
        result
        result.builder == builder
        result.marshaller == marshaller
        result.unmarshaller == unmarshaller
        result.clazz == Branding
    }
}