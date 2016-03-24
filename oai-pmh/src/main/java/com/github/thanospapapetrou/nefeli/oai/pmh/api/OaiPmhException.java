package com.github.thanospapapetrou.nefeli.oai.pmh.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Error;

/**
 * Exception representing a list of OAI-PMH errors.
 * 
 * @author thanos
 */
public class OaiPmhException extends Exception {
	private static final long serialVersionUID = 0L;

	private final List<Error> errors;

	/**
	 * Construct a new OAI-PMH exception.
	 * 
	 * @param errors
	 *            the OAI-PMH errors to include
	 */
	public OaiPmhException(final List<Error> errors) {
		this.errors = new ArrayList<Error>(Objects.requireNonNull(errors, "Errors must not be null"));
		this.errors.removeAll(Collections.singleton(null));
		if (this.errors.isEmpty()) {
			throw new IllegalArgumentException("Errors must contain at least one non null element");
		}
	}

	/**
	 * Get the OAI-PMH errors.
	 * 
	 * @return the OAI-PMH errors
	 */
	public List<Error> getErrors() {
		return Collections.unmodifiableList(errors);
	}
}
