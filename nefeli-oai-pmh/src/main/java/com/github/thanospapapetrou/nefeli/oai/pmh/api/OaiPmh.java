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

/**
 * Interface representing the OAI-PMH protocol.
 * @author thanos
 *
 */
public interface OaiPmh {
	/**
	 * Perform an OAI-PMH <code>Identify</code> request.
	 * @return the <code>Identify</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public Identify identify() throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListMetadataFormats</code> request.
	 * @param identifier the <code>identifier</code> request argument or <code>null</code> to leave it unspecified
	 * @return the <code>ListMetadataFormats</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListMetadataFormats listMetadataFormats(final URI identifier) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListSets</code> request with no <code>resumptionToken</code> argument.
	 * @return the <code>ListSets</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListSets listSets() throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListSets</code> request with a <code>resumptionToken</code> argument. 
	 * @param resumptionToken the <code>resumptionToken</code> request argument
	 * @return the <code>ListSets</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListSets listSets(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListIdentifiers</code> request with no <code>resumptionToken</code> argument.
	 * @param metadataPrefix the <code>metadataPrefix</code> request argument
	 * @param from the <code>from</code> request argument or <code>null</code> to leave it unspecified
	 * @param until the <code>until</code> request argument or <code>null</code> to leave it unspecified
	 * @param set the <code>set</code> request argument or <code>null</code> to leave it unspecified
	 * @return the <code>ListIdentifiers</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListIdentifiers listIdentifiers(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListIdentifiers</code> request with a <code>resumptionToken</code> argument.
	 * @param resumptionToken the <code>resumptionToken</code> request argument
	 * @return the <code>ListIdentifiers</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListIdentifiers listIdentifiers(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListRecords</code> request with no <code>resumptionToken</code> argument.
	 * @param metadataPrefix the <code>metadataPrefix</code> request argument
	 * @param from the <code>from</code> request argument or <code>null</code> to leave it unspecified
	 * @param until the <code>until</code> request argument or <code>null</code> to leave it unspecified
	 * @param set the <code>set</code> request argument or <code>null</code> to leave it unspecified
	 * @return the <code>ListRecords</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListRecords listRecords(final MetadataPrefix metadataPrefix, final Date from, final Date until, final SetSpec set) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>ListRecords</code> request with a <code>resumptionToken</code> argument.
	 * @param resumptionToken the <code>resumptionToken</code> request argument
	 * @return the <code>ListRecords</code> OAI-PMH response element
	 * @throws HTTPException if any HTTP error occurs
	 * @throws InterruptedException if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException if any I/O error occurs
	 * @throws OaiPmhException if any OAI-PMH errors occur
	 */
	public ListRecords listRecords(final String resumptionToken) throws HTTPException, InterruptedException, IOException, OaiPmhException;

	/**
	 * Perform an OAI-PMH <code>GetRecord</code> request.
	 * 
	 * @param identifier
	 *            the <code>identifier</code> request argument
	 * @param metadataPrefix
	 *            the <code>metadataPrefix</code> request argument
	 * @return the <code>GetRecord</code> OAI-PMH response element
	 * @throws HTTPException
	 *             if any HTTP error occurs
	 * @throws InterruptedException
	 *             if current thread is interrupted while waiting for the OAI-PMH response
	 * @throws IOException
	 *             if any I/O error occurs
	 * @throws OaiPmhException
	 *             if any OAI-PMH errors occur
	 */
	public GetRecord getRecord(final URI identifier, final MetadataPrefix metadataPrefix) throws HTTPException, InterruptedException, IOException, OaiPmhException;
}
