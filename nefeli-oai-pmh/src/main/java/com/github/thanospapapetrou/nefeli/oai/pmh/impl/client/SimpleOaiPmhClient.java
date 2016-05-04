package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.ws.http.HTTPException;

import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmhResponse;

/**
 * Simple OAI-PMH client.
 * 
 * @author thanos
 * @see <a href="http://www.openarchives.org/OAI/2.0/guidelines-harvester.htm">Guidelines for Harvester Implementers</a>
 */
public class SimpleOaiPmhClient extends AbstractOaiPmhClient {
	private static final String FROM = "From";

	private final String userAgent;
	private final InternetAddress from;

	/**
	 * Construct a new simple OAI-PMH client.
	 * 
	 * @param baseUrl
	 *            the base URL of the repository to connect to
	 * @param userAgent
	 *            the <code>User-Agent</code> HTTP header to use
	 * @param from
	 *            the <code>From</code> HTTP header to use
	 * @throws HTTPException
	 *             if any HTTP error occurs
	 * @throws InterruptedException
	 *             if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException
	 *             if any I/O error occurs
	 * @throws OaiPmhException
	 *             if any OAI-PMH errors occur
	 * @throws ParseException
	 *             if the <code>User-Agent</code> HTTP header provided is invalid
	 */
	public SimpleOaiPmhClient(final URL baseUrl, final String userAgent, final InternetAddress from) throws HTTPException, InterruptedException, IOException, OaiPmhException, ParseException {
		super(baseUrl);
		this.userAgent = UserAgentValidator.validate(userAgent);
		this.from = from;
	}

	@Override
	protected OaiPmhResponse request(final URL url, final Map<String, String> headers, final Map<String, String> parameters) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		if (userAgent != null) {
			headers.put(HttpHeaders.USER_AGENT, userAgent);
		}
		if (from != null) {
			headers.put(FROM, from.toString());
		}
		return super.request(url, headers, parameters);
	}
}
