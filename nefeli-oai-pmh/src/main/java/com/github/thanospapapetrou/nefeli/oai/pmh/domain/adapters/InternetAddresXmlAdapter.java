package com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters;

import javax.mail.internet.InternetAddress;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML adapter that marshals/unmarshals internet email addresses to/from strings.
 * 
 * @author thanos
 */
public class InternetAddresXmlAdapter extends XmlAdapter<String, InternetAddress> {
	@Override
	public InternetAddress unmarshal(final String string) throws Exception {
		return (string == null) ? null : new InternetAddress(string, true);
	}

	@Override
	public String marshal(final InternetAddress internetAddress) throws Exception {
		return (internetAddress == null) ? null : internetAddress.toString();
	}
}
