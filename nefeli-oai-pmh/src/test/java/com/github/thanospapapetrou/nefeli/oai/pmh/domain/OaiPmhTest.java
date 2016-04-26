package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for {@link OaiPmh}.
 * 
 * @author thanos
 */
@Test
public class OaiPmhTest {
	private static final String EXAMPLES = "/com/github/thanospapapetrou/nefeli/oai/pmh/examples";

	@DataProvider(name = "marshalUnmarshal")
	private static Iterator<Object[]> testMarshalUnmarshalData() throws URISyntaxException {
		final Iterator<File> examples = Arrays.asList(new File(OaiPmhTest.class.getResource(EXAMPLES).toURI()).listFiles()).iterator();
		return new Iterator<Object[]>() {
			@Override
			public boolean hasNext() {
				return examples.hasNext();
			}

			@Override
			public Object[] next() {
				final File example = examples.next();
				try (final InputStream inputStream = new FileInputStream(example)) {
					return new Object[] {OaiPmh.unmarshal(inputStream, Granularity.DAY)};
				} catch (final IOException e) {
					throw new TestException(String.format("Error loading OAI-PMH response from file %1$s", example), e);
				}
			}

			@Override
			public void remove() {
				examples.remove();
			}
		};
	}

	/**
	 * Test for {@link OaiPmh#marshal(java.io.OutputStream, Granularity)} and {@link OaiPmh#unmarshal(InputStream, Granularity)}.
	 * 
	 * @param oaiPmh
	 *            the OAI-PMH response to test with
	 * @throws IOException
	 *             if any errors occur
	 */
	@Test(dataProvider = "marshalUnmarshal")
	public void testMarshalUnmarshal(final OaiPmh oaiPmh) throws IOException {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		oaiPmh.marshal(outputStream, Granularity.DAY);
		Assert.assertEquals(OaiPmh.unmarshal(new ByteArrayInputStream(outputStream.toByteArray()), Granularity.DAY), oaiPmh);
	}
}
