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
		final Iterator<File> files = Arrays.asList(new File(OaiPmhTest.class.getResource(EXAMPLES).toURI()).listFiles()).iterator();
		return new Iterator<Object[]>() {
			@Override
			public boolean hasNext() {
				return files.hasNext();
			}

			@Override
			public Object[] next() {
				return new Object[] {files.next()};
			}

			@Override
			public void remove() {
				files.remove();
			}
		};
	}

	/**
	 * Test for {@link OaiPmh#marshal(java.io.OutputStream)} and {@link OaiPmh#unmarshal(InputStream)}.
	 * 
	 * @param example
	 *            the example to test with
	 * @throws IOException
	 *             if any errors occur
	 */
	@Test(dataProvider = "marshalUnmarshal")
	public void testMarshalUnmarshal(final File example) throws IOException {
		try (final InputStream inputStream = new FileInputStream(example)) {
			final OaiPmh oaiPmh = OaiPmh.unmarshal(inputStream, Granularity.SECONDS);
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			oaiPmh.marshal(outputStream, Granularity.SECONDS);
			Assert.assertEquals(OaiPmh.unmarshal(new ByteArrayInputStream(outputStream.toByteArray()), Granularity.SECONDS), oaiPmh);
		}
	}
}
