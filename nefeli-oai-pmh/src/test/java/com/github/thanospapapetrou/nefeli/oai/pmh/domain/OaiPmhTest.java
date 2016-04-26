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
				return new Object[] {examples.next()};
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
	 * @param example
	 *            the example to test with
	 * @throws IOException
	 *             if any errors occur
	 */
	@Test(dataProvider = "marshalUnmarshal")
	public void testMarshalUnmarshal(final File example) throws IOException {
		try (final InputStream inputStream = new FileInputStream(example)) {
			final OaiPmh oaiPmh = OaiPmh.unmarshal(inputStream, Granularity.DAY);
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			oaiPmh.marshal(outputStream, Granularity.DAY);
			Assert.assertEquals(OaiPmh.unmarshal(new ByteArrayInputStream(outputStream.toByteArray()), Granularity.DAY), oaiPmh);
		}
	}
}
