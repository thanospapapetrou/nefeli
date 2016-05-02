package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	private static final Map<String, Granularity> EXAMPLES = new HashMap<String, Granularity>() {
		private static final long serialVersionUID = 0L;

		{
			put("Error1", Granularity.SECONDS);
			put("Error2", Granularity.SECONDS);
			put("GetRecord1", Granularity.DAY);
			put("GetRecord2", Granularity.SECONDS);
			put("GetRecord3", Granularity.SECONDS);
			put("Identify", Granularity.SECONDS);
			put("ListIdentifiers1", Granularity.DAY);
			put("ListIdentifiers2", Granularity.DAY);
			put("ListIdentifiers3", Granularity.SECONDS);
			put("ListMetadataFormats1", Granularity.SECONDS);
			put("ListMetadataFormats2", Granularity.SECONDS);
			put("ListMetadataFormats3", Granularity.SECONDS);
			put("ListRecords1", Granularity.DAY);
			put("ListRecords2", Granularity.SECONDS);
			put("ListRecords3", Granularity.SECONDS);
			put("ListSets1", Granularity.SECONDS);
			put("ListSets2", Granularity.SECONDS);
		}
	};
	private static final String EXAMPLE = "/com/github/thanospapapetrou/nefeli/oai/pmh/examples/%1$s.xml";

	@DataProvider(name = "marshalUnmarshal")
	private static Iterator<Object[]> testMarshalUnmarshalData() throws URISyntaxException {
		final Iterator<Map.Entry<String, Granularity>> examples = EXAMPLES.entrySet().iterator();
		return new Iterator<Object[]>() {
			@Override
			public boolean hasNext() {
				return examples.hasNext();
			}

			@Override
			public Object[] next() {
				final Map.Entry<String, Granularity> example = examples.next();
				return new Object[] {OaiPmhTest.class.getResource(String.format(EXAMPLE, example.getKey())), example.getValue()};
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
	 * @param granularity
	 *            the granularity to test with
	 * @throws IOException
	 *             if any errors occur
	 */
	@Test(dataProvider = "marshalUnmarshal")
	public void testMarshalUnmarshal(final URL example, final Granularity granularity) throws IOException {
		try (final InputStream inputStream = example.openStream()) {
			final OaiPmh oaiPmh = OaiPmh.unmarshal(inputStream, granularity);
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			oaiPmh.marshal(outputStream, granularity);
			Assert.assertEquals(OaiPmh.unmarshal(new ByteArrayInputStream(outputStream.toByteArray()), granularity), oaiPmh);
		}
	}
}
