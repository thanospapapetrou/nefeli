package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.http.HTTPException;

import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmh;
import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.GetRecord;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Identify;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListIdentifiers;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListMetadataFormats;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListRecords;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListSets;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.MetadataPrefix;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.SetSpec;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Verb;

public abstract class AbstractOaiPmhClient implements OaiPmh {
	private static final String VERB = "verb";
	private static final String IDENTIFIER = "identifier";
	private static final String METADATA_PREFIX = "metadataPrefix";
	private static final String FROM = "from";
	private static final String UNTIL = "until";
	private static final String SET = "set";
	private static final String RESUMPTION_TOKEN = "resumptionToken";

	protected URL baseUrl;
	protected Identify identify;

	protected AbstractOaiPmhClient(final URL baseUrl) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		this.baseUrl = Objects.requireNonNull(baseUrl, "Base URL must not be null");
		identify = identify();
	}

	@Override
	public Identify identify() throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.IDENTIFY).getIdentify();
	}

	@Override
	public ListMetadataFormats listMetadataFormats(final URI identifier) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, Verb.LIST_METADATA_FORMATS.toString());
				if (identifier != null) {
					put(IDENTIFIER, identifier.toString());
				}
			}
		}).getListMetadataFormats();
	}

	@Override
	public ListSets listSets() throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_SETS).getListSets();
	}

	@Override
	public ListSets listSets(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_SETS, resumptionToken).getListSets();
	}

	@Override
	public ListIdentifiers listIdentifiers(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_IDENTIFIERS, metadataPrefix, from, until, set).getListIdentifiers();
	}

	@Override
	public ListIdentifiers listIdentifiers(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_IDENTIFIERS, resumptionToken).getListIdentifiers();
	}

	@Override
	public ListRecords listRecords(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_RECORDS, metadataPrefix, from, until, set).getListRecords();
	}

	@Override
	public ListRecords listRecords(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Verb.LIST_RECORDS, resumptionToken).getListRecords();
	}

	@Override
	public GetRecord getRecord(final URI identifier, final MetadataPrefix metadataPrefix) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, Verb.GET_RECORD.toString());
				put(IDENTIFIER, Objects.requireNonNull(identifier, "Identifier must not be null").toString());
				put(METADATA_PREFIX, Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null").toString());
			}
		}).getGetRecord();
	}

	protected Response request(final Map<String, String> headers, final Map<String, String> parameters) throws HTTPException, InterruptedException, IOException {
		final Response response = get(baseUrl, new HashMap<String, String>() {
			{
				put(HttpHeaders.ACCEPT, MediaType.TEXT_XML);
				put(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
			}
		}, parameters);
		final String contentType = response.getHeaderString(HttpHeaders.CONTENT_TYPE);
		if (contentType == null) {
			throw new IOException("Response must specify a " + HttpHeaders.CONTENT_TYPE + " header");
		}
		final MediaType mediaType = MediaType.valueOf(contentType);
		if (!MediaType.TEXT_XML_TYPE.isCompatible(mediaType)) {
			throw new IOException("Response content type must be " + MediaType.TEXT_XML_TYPE.toString());
		}
		if (mediaType.getParameters().containsKey(MediaType.CHARSET_PARAMETER) && StandardCharsets.UTF_8.equals(Charset.forName(mediaType.getParameters().get(MediaType.CHARSET_PARAMETER)))) {
			throw new IOException("Response charset is not " + StandardCharsets.UTF_8.name());
		}
		return response;
	}

	protected com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh parseOaiPmh(final InputStream inputStream) throws IOException, OaiPmhException {
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

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(Collections.singletonMap(VERB, verb.toString()));
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb, final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, verb.toString());
				put(RESUMPTION_TOKEN, Objects.requireNonNull(resumptionToken, "Resumption token must not be null"));
			}
		});
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Verb verb, final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		return request(new HashMap<String, String>() {
			private static final long serialVersionUID = 0L;

			{
				put(VERB, verb.toString());
				put(METADATA_PREFIX, Objects.requireNonNull(metadataPrefix, "Metadata prefix must not be null").toString());
				if (from != null) {
					put(FROM, identify.getGranularity().format(from));
				}
				if (until != null) {
					put(UNTIL, identify.getGranularity().format(until));
				}
				if ((from != null) && (until != null) && from.after(until)) {
					throw new IllegalArgumentException("Until must be after before");
				}
				if (set != null) {
					put(SET, set.toString());
				}
			}
		});
	}

	private com.github.thanospapapetrou.nefeli.oai.pmh.domain.OaiPmh request(final Map<String, String> parameters) throws HTTPException, InterruptedException, IOException, OaiPmhException {
		try (final InputStream input = request(new HashMap<String, String>(), parameters).readEntity(InputStream.class)) {
			return parseOaiPmh(input);
		}
	}

	private Response get(final URL url, final Map<String, String> headers, final Map<String, String> parameters) throws HTTPException, InterruptedException, IOException {
		final Client client = ClientBuilder.newClient();
		try {
			WebTarget target = client.target(url.toURI());
			for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
				target = target.queryParam(parameter.getKey(), parameter.getValue());
			}
			Invocation.Builder invocationBuilder = target.request();
			for (final Map.Entry<String, String> header : headers.entrySet()) {
				invocationBuilder = invocationBuilder.header(header.getKey(), header.getValue());
			}
			final Response response = invocationBuilder.get();
			try {
				final String location = response.getHeaderString(HttpHeaders.LOCATION);
				final String retryAfter = response.getHeaderString(HttpHeaders.RETRY_AFTER);
				switch (Response.Status.fromStatusCode(response.getStatus())) {
				case OK:
					return response;
				case MOVED_PERMANENTLY:
				case FOUND:
				case SEE_OTHER:
				case TEMPORARY_REDIRECT:
					if (location == null) {
						throw new HTTPException(response.getStatus());
					} else { // redirect
						return get(new URL(location), headers, Collections.<String, String> emptyMap());
					}
				case METHOD_NOT_ALLOWED:
				case REQUEST_URI_TOO_LONG:
					return post(url, headers, parameters);
				case SERVICE_UNAVAILABLE:
					if (retryAfter == null) {
						throw new HTTPException(response.getStatus());
					} else {
						try {
							Thread.sleep(TimeUnit.SECONDS.toMillis(Long.valueOf(retryAfter)));
							return get(url, headers, parameters);
						} catch (final NumberFormatException e) {
							throw new HTTPException(response.getStatus());
						}
					}
				default:
					throw new HTTPException(response.getStatus());
				}
			} finally {
				response.close();
			}
		} catch (final URISyntaxException e) {
			throw new IOException("Invalid URI " + url, e);
		} finally {
			client.close();
		}
	}

	private Response post(final URL url, final Map<String, String> headers, final Map<String, String> parameters) throws HTTPException, MalformedURLException, InterruptedException, IOException {
		final Client client = ClientBuilder.newClient();
		try {
			Form form = new Form();
			for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
				form = form.param(parameter.getKey(), parameter.getValue());
			}
			Invocation.Builder invocationBuilder = client.target(url.toURI()).request();
			for (final Map.Entry<String, String> header : headers.entrySet()) {
				invocationBuilder = invocationBuilder.header(header.getKey(), header.getValue());
			}
			final Response response = invocationBuilder.post(Entity.form(form));
			try {
				final String location = response.getHeaderString(HttpHeaders.LOCATION);
				final String retryAfter = response.getHeaderString(HttpHeaders.RETRY_AFTER);
				switch (Response.Status.fromStatusCode(response.getStatus())) {
				case OK:
					return response;
				case MOVED_PERMANENTLY:
				case FOUND:
				case SEE_OTHER:
					if (location == null) {
						throw new HTTPException(response.getStatus());
					} else {
						return get(new URL(location), headers, Collections.<String, String> emptyMap());
					}
				case TEMPORARY_REDIRECT:
					if (location == null) {
						throw new HTTPException(response.getStatus());
					} else {
						return post(new URL(location), headers, Collections.<String, String> emptyMap());
					}
				case METHOD_NOT_ALLOWED:
					return get(url, headers, parameters);
				case SERVICE_UNAVAILABLE:
					if (retryAfter == null) {
						throw new HTTPException(response.getStatus());
					} else {
						try {
							Thread.sleep(TimeUnit.SECONDS.toMillis(Long.valueOf(retryAfter)));
							return post(url, headers, parameters);
						} catch (final NumberFormatException e) {
							throw new HTTPException(response.getStatus());
						}
					}
				default:
					throw new HTTPException(response.getStatus());
				}
			} finally {
				response.close();
			}
		} catch (final URISyntaxException e) {
			throw new IOException("Invalid URI " + url, e);
		} finally {
			client.close();
		}
	}
}