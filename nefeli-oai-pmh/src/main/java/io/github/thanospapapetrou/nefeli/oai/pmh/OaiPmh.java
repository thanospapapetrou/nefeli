package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

import jakarta.ws.rs.WebApplicationException;

import org.openarchives.oai._2.GetRecord;
import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListRecords;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhResponse;
import org.openarchives.oai._2.SetSpec;

/**
 * <a href="https://www.openarchives.org/OAI/openarchivesprotocol.html">OAI-PMH 2.0</a>
 */
public interface OaiPmh {
    String ARGUMENT_FROM = "from";
    String ARGUMENT_IDENTIFIER = "identifier";
    String ARGUMENT_METADATA_PREFIX = "metadataPrefix";
    String ARGUMENT_RESUMPTION_TOKEN = "resumptionToken";
    String ARGUMENT_SET = "set";
    String ARGUMENT_UNTIL = "until";
    String ARGUMENT_VERB = "verb";

    OaiPmhResponse<Identify> identify()
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListSets> listSets()
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListSets> listSets(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final SetSpec set) throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<ListRecords> listRecords(final String resumptionToken)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;

    OaiPmhResponse<GetRecord> getRecord(final URI identifier, final String metadataPrefix)
            throws IOException, OaiPmhException, RetryAfterException, WebApplicationException;
}
