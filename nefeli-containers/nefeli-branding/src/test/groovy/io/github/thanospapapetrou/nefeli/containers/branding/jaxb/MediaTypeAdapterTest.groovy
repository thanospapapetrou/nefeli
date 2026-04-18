package io.github.thanospapapetrou.nefeli.containers.branding.jaxb

import jakarta.ws.rs.core.MediaType
import spock.lang.Specification
import spock.lang.Unroll

class MediaTypeAdapterTest extends Specification {
    private static final List<String> MEDIA_TYPES = ['text/dsssl', 'text/css', 'text/xsl']

    private final MediaTypeAdapter adapter = new MediaTypeAdapter()

    @Unroll('Test marshal (media type: #mediaType)')
    def 'Test marshal'(final String mediaType) {
        expect:
        adapter.marshal(MediaType.valueOf(mediaType)) == mediaType
        where:
        mediaType << MEDIA_TYPES
    }

    @Unroll('Test unmarshal (media type: #mediaType)')
    def 'Test unmarshal'(final MediaType mediaType) {
        expect:
        adapter.unmarshal(mediaType.toString()) == mediaType
        where:
        mediaType << MEDIA_TYPES.collect(MediaType.&valueOf)
    }
}