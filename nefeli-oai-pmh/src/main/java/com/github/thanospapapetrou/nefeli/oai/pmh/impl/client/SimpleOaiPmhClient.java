package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmh;
import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;

public class SimpleOaiPmhClient extends AbstractOaiPmhClient implements OaiPmh {
	public static void main(final String[] arguments) {
		final ParsePosition parsePosition = new ParsePosition(0);
		for (final String s : new String[] {"foo", "foo/bar", "foo/bar (buz)", "foo/bar (foo (bar (buz)))", "foo/bar (foo (bar (buz))) foo/bar (foo (bar (buz))) foo/bar (foo (bar (buz)))"}) {
			try {
				System.out.println(s);
				parsePosition.setIndex(0);
				parsePosition.setErrorIndex(-1);
				parseUserAgent(s, parsePosition);
				System.out.println("OK");
			} catch (final ParseException e) {
//				e.printStackTrace();
				System.out.println("Error " + e.getMessage());
			}
		}
	}



	private static final String FROM = "From";

	private final String userAgent;
	private final InternetAddress from;

	public SimpleOaiPmhClient(final URL baseUrl, final String userAgent, final InternetAddress from) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		super(baseUrl);
		this.userAgent = userAgent;
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
