package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

@ApplicationScoped
public class InternetAddressAdapter extends XmlAdapter<String, InternetAddress> {
    @Override
    public String marshal(final InternetAddress address) {
        return (address == null) ? null : address.getAddress();
    }

    @Override
    public InternetAddress unmarshal(final String string) throws AddressException {
        return (string == null) ? null : new InternetAddress(string);
    }
}
