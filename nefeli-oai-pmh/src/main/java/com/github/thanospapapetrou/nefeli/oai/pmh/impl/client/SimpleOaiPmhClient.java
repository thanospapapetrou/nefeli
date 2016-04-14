package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmh;
import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;

public class SimpleOaiPmhClient extends AbstractOaiPmhClient implements OaiPmh {
	private static final String FROM = "From";

	private final String userAgent;
	private final InternetAddress from;

	public SimpleOaiPmhClient(final URL baseUrl, final String userAgent, final InternetAddress from) throws HTTPException, InterruptedException, IOException, OaiPmhException, ParseException {
		super(baseUrl);
		this.userAgent = UserAgentValidator.validate(userAgent);
		this.from = from;
	}

	@Override
	protected Response request(final Map<String, String> headers, final Map<String, String> parameters) throws HTTPException, InterruptedException, IOException {
		if (userAgent != null) {
			headers.put(HttpHeaders.USER_AGENT, userAgent);
		}
		if (from != null) {
			headers.put(FROM, from.toString());
		}
		return super.request(headers, parameters);
	}
}
