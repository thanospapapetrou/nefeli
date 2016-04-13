package com.github.thanospapapetrou.nefeli.oai.pmh.api;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import javax.xml.ws.http.HTTPException;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.GetRecord;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Identify;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListIdentifiers;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListMetadataFormats;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListRecords;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListSets;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.MetadataPrefix;
import com.github.thanospapapetrou.nefeli.oai.pmh.domain.SetSpec;

public interface OaiPmh {
	public Identify identify() throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListMetadataFormats listMetadataFormats(final URI identifier) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListSets listSets() throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListSets listSets(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListIdentifiers listIdentifiers(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListIdentifiers listIdentifiers(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListRecords listRecords(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public ListRecords listRecords(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	public GetRecord getRecord(final URI identifier, final MetadataPrefix metadataPrefix) throws HTTPException, InterruptedException, IOException, OaiPmhException;
}
