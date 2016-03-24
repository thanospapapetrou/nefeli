package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.github.thanospapapetrou.nefeli.oai.pmh.api.HttpException;
import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmh;
import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.GetRecord;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Identify;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListIdentifiers;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListMetadataFormats;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListRecords;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListSets;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.MetadataPrefix;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.SetSpec;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Verb;

public class SimpleOaiPmhClient implements OaiPmh {
	private static final String VERB = "verb";
	private static final String IDENTIFIER = "identifier";
	private static final String METADATA_PREFIX = "metadataPrefix";
	private static final String FROM = "from";
	private static final String UNTIL = "until";
	private static final String SET = "set";
	private static final String RESUMPTION_TOKEN = "resumptionToken";
	private static final String HTTP_FROM = "From";

	private final URL baseUrl;
	private final String userAgent; // TODO use token and comment
	private final InternetAddress from;

	public static void main(final String[] arguments) {
		try {
			final OaiPmh oaiPmh = new SimpleOaiPmhClient(new URL("http://www.informatik.uni-stuttgart.de/cgi-bin/OAI/OAI.pl"), "foo bar buz", new InternetAddress("foo@bar.buz"));
			final Identify identify = oaiPmh.identify();
			System.out.println(identify.getBaseUrl());
			System.out.println(identify.getRepositoryName());
			System.out.println(identify.getProtocolVersion());
			System.out.println(identify.getGranularity());
		} catch (final AddressException | HttpException | InterruptedException | IOException | OaiPmhException e) {
			e.printStackTrace();
		}
	}
	
	private static com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh parseOaiPmh(final InputStream inputStream) throws IOException, OaiPmhException {
		try (final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
			final com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh oaiPmh = (com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh) JAXBContext.newInstance(com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh.class).createUnmarshaller().unmarshal(reader);
			if (!oaiPmh.getErrors().isEmpty()) {
				throw new OaiPmhException(oaiPmh.getErrors());
			}
			return oaiPmh;
		} catch (final JAXBException e) {
			throw new IOException("Error parsing OAI-PMH response", e);
		}
	}
	
	public SimpleOaiPmhClient(final URL baseUrl, final String userAgent, final InternetAddress from) {
		this.baseUrl = Objects.requireNonNull(baseUrl, "Base URL must not be null");
		this.userAgent = Objects.requireNonNull(userAgent, "User agent must not be null");
		this.from = Objects.requireNonNull(from, "From must not be null");
	}

	@Override
	public Identify identify() throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.IDENTIFY).getIdentify();
	}

	@Override
	public ListMetadataFormats listMetadataFormats(final URI identifier) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, Verb.LIST_METADATA_FORMATS.toString());
				put(IDENTIFIER, identifier.toString());
			}
		}).getListMetadataFormats();
	}

	@Override
	public ListSets listSets() throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_SETS).getListSets();
	}

	@Override
	public ListSets listSets(final String resumptionToken) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_SETS, resumptionToken).getListSets();
	}

	@Override
	public ListIdentifiers listIdentifiers(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_IDENTIFIERS, metadataPrefix, from, until, set).getListIdentifiers();
	}

	@Override
	public ListIdentifiers listIdentifiers(final String resumptionToken) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_IDENTIFIERS, resumptionToken).getListIdentifiers();
	}

	@Override
	public ListRecords listRecords(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_RECORDS, metadataPrefix, from, until, set).getListRecords();
	}

	@Override
	public ListRecords listRecords(final String resumptionToken) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.IDENTIFY, resumptionToken).getListRecords();
	}

	@Override
	public GetRecord getRecord(final URI identifier, final MetadataPrefix metadataPrefix) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, Verb.GET_RECORD.toString());
				put(IDENTIFIER, identifier.toString());
				put(METADATA_PREFIX, metadataPrefix.toString());
			}
		}).getGetRecord();
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(Collections.singletonMap(VERB, verb.toString()));
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb, final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, verb.toString());
				put(METADATA_PREFIX, metadataPrefix.toString());
				final Granularity granularity = identify().getGranularity();
				put(FROM, granularity.format(from));
				put(UNTIL, granularity.format(until));
				put(SET, set.toString());
			}
		});
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb, final String resumptionToken) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, verb.toString());
				put(RESUMPTION_TOKEN, Objects.requireNonNull(resumptionToken, "Resumption token must not be null"));
			}
		});
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Map<String, String> parameters) throws HttpException, InterruptedException, IOException, OaiPmhException {
		return request(baseUrl, false, parameters);
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final URL url, final boolean post, final Map<String, String> parameters) throws HttpException, InterruptedException, IOException, OaiPmhException {
		final Client client = ClientBuilder.newClient();
		try {
			final Response response = post ? post(client, url, parameters) : get(client, url, parameters);
			try {
				switch (Response.Status.fromStatusCode(response.getStatus())) {
				case OK:
					try (final InputStream input = response.readEntity(InputStream.class)) {
						return parseOaiPmh(input);
					}
				case FOUND:
					final String location = response.getHeaderString(HttpHeaders.LOCATION);
					if (location == null) {
						throw new HttpException(response);
					} else {
						return request(new URL(location), post, Collections.<String, String> emptyMap());
					}
				case REQUEST_URI_TOO_LONG:
					if (post) {
						throw new HttpException(response);
					} else {
						return request(url, true, parameters);
					}
				case SERVICE_UNAVAILABLE:
					final String retryAfter = response.getHeaderString(HttpHeaders.RETRY_AFTER);
					if (retryAfter == null) {
						throw new HttpException(response);
					} else {
						Thread.sleep(TimeUnit.SECONDS.toMillis(Long.parseLong(retryAfter)));
						return request(url, post, parameters);
					}
				default:
					throw new HttpException(response);
				}
			} finally {
				response.close();
			}
		} finally {
			client.close();
		}
	}

	private Response get(final Client client, final URL url, final Map<String, String> parameters) throws IOException {
		try {
			WebTarget target = client.target(url.toURI());
			for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
				target = target.queryParam(parameter.getKey(), parameter.getValue());
			}
			return target.request(MediaType.TEXT_XML_TYPE).acceptEncoding(StandardCharsets.UTF_8.name()).header(HttpHeaders.USER_AGENT, userAgent).header(HTTP_FROM, from.toString()).get();
		} catch (final URISyntaxException e) {
			throw new IOException("Error converting URL to URI", e);
		}
	}

	private Response post(final Client client, final URL url, final Map<String, String> parameters) throws IOException {
		try {
			Form form = new Form();
			for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
				form = form.param(parameter.getKey(), parameter.getValue());
			}
			return client.target(url.toURI()).request(MediaType.TEXT_XML_TYPE).acceptEncoding(StandardCharsets.UTF_8.name()).header(HttpHeaders.USER_AGENT, userAgent).header(HTTP_FROM, from.toString()).post(Entity.form(form));
		} catch (final URISyntaxException e) {
			throw new IOException("Error converting URL to URI", e);
		}
	}
}
