package io.github.thanospapapetrou.nefeli;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

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
