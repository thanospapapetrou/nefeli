package com.github.thanospapapetrou.nefeli.oai.pmh.api;

import java.util.Objects;

import javax.ws.rs.core.Response;

/**
 * Exception representing an HTTP error.
 * 
 * @author thanos
 * @see <a href="https://www.ietf.org/rfc/rfc7231.txt">Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content</a>
 */
public class HttpException extends Exception {
	private static final long serialVersionUID = 0L;

	private final int statusCode;

	/**
	 * Construct a new HTTP exception.
	 * 
	 * @param statusCode
	 *            the status code
	 * @param reasonPhrase
	 *            the reason phrase
	 */
	public HttpException(final int statusCode, final String reasonPhrase) {
		super(reasonPhrase);
		this.statusCode = statusCode;
	}

	/**
	 * Construct a new HTTP exception from an HTTP response.
	 * 
	 * @param response
	 *            the HTTP response
	 */
	public HttpException(final Response response) {
		this(Objects.requireNonNull(response, "Response must not be null").getStatus(), response.getStatusInfo().getReasonPhrase());
	}

	/**
	 * Get the status code.
	 * 
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}
}
