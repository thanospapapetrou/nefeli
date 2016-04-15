package com.github.thanospapapetrou.nefeli.oai.pmh.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class OaiPmhTest {
	@DataProvider(name = "marshalUnmarshal")
	private static Iterator<Object[]> testMarshalUnmarshalData() {
		final List<Object[]> data = new ArrayList<Object[]>();
		for (final String example : new String[] {"/com/github/thanospapapetrou/nefeli/oai/pmh/domain/Error.xml"}) {
			data.add(new Object[] {example});
		}
		return data.iterator();
	}
	
	@Test(dataProvider = "marshalUnmarshal")
	public void testMarshalUnmarshal(final String example) throws IOException {
		try (final InputStream inputStream = this.getClass().getResourceAsStream(example)) {
			final OaiPmh oaiPmh = OaiPmh.unmarshal(inputStream);
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			oaiPmh.marshal(outputStream);
			Assert.assertEquals(OaiPmh.unmarshal(new ByteArrayInputStream(outputStream.toByteArray())), oaiPmh);
		}
	}
}
