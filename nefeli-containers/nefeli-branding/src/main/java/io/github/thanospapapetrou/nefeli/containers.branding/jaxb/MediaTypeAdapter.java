package io.github.thanospapapetrou.nefeli.containers.branding.jaxb;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.internet.AddressException;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

@ApplicationScoped
public class MediaTypeAdapter extends XmlAdapter<String, MediaType> {
    @Override
    public String marshal(final MediaType mediaType) {
        return mediaType.toString();
    }

    @Override
    public MediaType unmarshal(final String string) throws AddressException {
        return MediaType.valueOf(string);
    }
}
