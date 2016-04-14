package com.github.thanospapapetrou.nefeli.oai.pmh.impl.client;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate a <code>User-Agent</code> HTTP header value using the following BNF:
 * <code>
 * <UserAgent>        ::= <product> <linearWhiteSpace> <UserAgent>
 *                      | <comment> <linearWhiteSpace> <UserAgent>
 *                      | <product>
 *                      | <comment>
 * <product>          ::= <token> "/" <token>
 *                      | <token>
 * <comment>          ::= "(" (<ctext> | <comment> | <quotedPair>)* ")"
 * <token>            ::= [\x00-\x7F&&[^\p{Cntrl}]&&[^\(\)<>@\,;:\\"/\[\]\?=\{\} \t]]+
 * <ctext>            ::= ([\x00-\xFF&&[^\p{Cntrl}]&&[^\(\)]]+|((\r\f)?( \t)+))+
 * <quotedPair>       ::= \\[\x00-\x7F]
 * <linearWhiteSpace> ::= (\r\f)?( \t)+
 * </code>
 * Actually this class tries to parse the following equivalent LL(1) BNF:
 * <code>
 * <UserAgent>        ::= <token> <tokenSuffix>
 *                      | "(" <commentSuffix>
 * <tokenSuffix>      ::= "/" <token> <linearWhiteSpace> <UserAgent>
 *                      | "/" <token>
 *                      | <linearWhiteSpace> <UserAgent>
 *                      | ε
 * <commentSuffix>    ::= <ctext> <commentSuffix>
 *                      | "(" <commentSuffix> <commentSuffix>
 *                      | <quotedPair> <commentSuffix>
 *                      | ")"
 * </code>
 * 
 * @see <a href="http://www.ietf.org/rfc/rfc2616">Hypertext Transfer Protocol -- HTTP/1.1</a>
 * @author thanos
 */
public class UserAgentValidator {
	private static enum Token {
		CTEXT("([\\x00-\\xFF&&[^\\p{Cntrl}]&&[^\\(\\)]]+|((\\r\\f)?( \\t)+))+", "ctext"),
		END_OF_INPUT("$", "end of input"),
		LEFT_PARENTHESIS("\\(", "left parenthesis"),
		LINEAR_WHITESPACE("(\\r\\f)?( |\\t)+", "linear whitespace"),
		QUOTED_PAIR("\\\\[\\x00-\\x7F]", "quoted pair"),
		RIGHT_PARENTHESIS("\\)", "right parenthesis"),
		SLASH("/", "slash"),
		TOKEN("[\\x00-\\x7F&&[^\\p{Cntrl}]&&[^\\(\\)<>@\\,;:\\\\\"/\\[\\]\\?=\\{\\} \\t]]+", "token");

		private final Pattern pattern;
		private final String name;

		private Token(final String regex, final String name) {
			this.pattern = Pattern.compile(regex);
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private final String userAgent;
	private final ParsePosition parsePosition;

	/**
	 * Validate a <code>User-Agent</code> HTTP header value.
	 * 
	 * @param userAgent
	 *            the <code>User-Agent</code> HTTP header value to validate
	 * @return the validated <code>User-Agent</code> HTTP header value
	 * @throws ParseException
	 *             if the <code>User-Agent</code> HTTP header value can not be validated
	 */
	public static String validate(final String userAgent) throws ParseException {
		new UserAgentValidator(userAgent).parseUserAgent();
		return userAgent;
	}

	private static String or(final Token... tokens) {
		if (tokens.length == 1) {
			return tokens[0].toString();
		} else if (tokens.length == 2) {
			return String.format("%1$s or %2$s", tokens[0], tokens[1]);
		} else {
			return String.format("%1$s, %2$s", tokens[0], or(Arrays.asList(tokens).subList(1, tokens.length).toArray(new Token[0])));
		}
	}

	private UserAgentValidator(final String userAgent) {
		this.userAgent = userAgent;
		this.parsePosition = new ParsePosition(0);
	}

	@SuppressWarnings("incomplete-switch")
	private void parseUserAgent() throws ParseException {
		switch (parse(Token.TOKEN, Token.LEFT_PARENTHESIS)) {
		case TOKEN: // <UserAgent> ::= <token> <tokenSuffix>
			parseTokenSuffix();
			return;
		case LEFT_PARENTHESIS: // <UserAgent> ::= "(" <commentSuffix>
			parseCommentSuffix();
			return;
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void parseTokenSuffix() throws ParseException {
		switch (parse(Token.SLASH, Token.LINEAR_WHITESPACE, Token.END_OF_INPUT)) {
		case SLASH: // <tokenSuffix> ::= "/" <token> <linearWhiteSpace> <UserAgent> | "/" <token>
			parse(Token.TOKEN);
			switch (parse(Token.LINEAR_WHITESPACE, Token.END_OF_INPUT)) {
			case LINEAR_WHITESPACE: // <tokenSuffix> ::= "/" <token> <linearWhiteSpace> <UserAgent>
				parseUserAgent();
				return;
			case END_OF_INPUT: // <tokenSuffix> ::= "/" <token>
				return;
			}
		case LINEAR_WHITESPACE: // <tokenSuffix> ::= <linearWhiteSpace> <UserAgent>
			parseUserAgent();
			return;
		case END_OF_INPUT: // <tokenSuffix> ::= ε
			return;
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void parseCommentSuffix() throws ParseException {
		switch (parse(Token.CTEXT, Token.LEFT_PARENTHESIS, Token.QUOTED_PAIR, Token.RIGHT_PARENTHESIS)) {
		case CTEXT: // <commentSuffix> ::= <ctext> <commentSuffix>
			parseCommentSuffix();
			return;
		case LEFT_PARENTHESIS: // <commentSuffix> ::= "(" <commentSuffix> <commentSuffix>
			parseCommentSuffix();
			parseCommentSuffix();
			return;
		case QUOTED_PAIR: // <commentSuffix> ::= <quotedPair> <commentSuffix>
			parseCommentSuffix();
			return;
		case RIGHT_PARENTHESIS: // <commentSuffix> ::= ")"
			return;
		}
	}

	private Token parse(final Token... expected) throws ParseException {
		for (final Token token : expected) {
			final Matcher matcher = token.pattern.matcher(userAgent).region(parsePosition.getIndex(), userAgent.length());
			if (matcher.lookingAt()) {
				parsePosition.setIndex(matcher.end());
				return token;
			}
		}
		for (final Token token : Token.values()) {
			if (!Arrays.asList(expected).contains(token)) {
				final Matcher matcher = token.pattern.matcher(userAgent).region(parsePosition.getIndex(), userAgent.length());
				if (matcher.lookingAt()) {
					parsePosition.setErrorIndex(parsePosition.getIndex());
					throw new ParseException(String.format("Unexpected %1$s, expected %2$s", token, or(expected)), parsePosition.getIndex());
				}
			}
		}
		parsePosition.setErrorIndex(parsePosition.getIndex());
		throw new ParseException(String.format("Unparsable input: %1$s", userAgent.substring(parsePosition.getIndex())), parsePosition.getIndex());
	}
}
