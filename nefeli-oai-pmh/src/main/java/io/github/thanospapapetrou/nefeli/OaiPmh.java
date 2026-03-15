package io.github.thanospapapetrou.nefeli;

import java.net.URI;
import java.time.Instant;

import org.openarchives.oai._2.GetRecord;
import org.openarchives.oai._2.Identify;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListMetadataFormats;
import org.openarchives.oai._2.ListRecords;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.OaiPmhResponse;

interface OaiPmh {
    String ARGUMENT_IDENTIFIER = "identifier";
    String ARGUMENT_FROM = "from";
    String ARGUMENT_METADATA_PREFIX = "metadataPrefix";
    String ARGUMENT_RESUMPTION_TOKEN = "resumptionToken";
    String ARGUMENT_SET = "set";
    String ARGUMENT_UNTIL = "until";
    String ARGUMENT_VERB = "verb";

    OaiPmhResponse<Identify> identify() throws OaiPmhException;

    OaiPmhResponse<ListMetadataFormats> listMetadataFormats(final URI identifier) throws OaiPmhException;

    OaiPmhResponse<ListSets> listSets() throws OaiPmhException;

    OaiPmhResponse<ListSets> listSets(final String resumptionToken) throws OaiPmhException;

    OaiPmhResponse<ListIdentifiers> listIdentifiers(final String metadataPrefix, final Instant from,
            final Instant until, final String set) throws OaiPmhException;

    OaiPmhResponse<ListIdentifiers> listIdentifiers(final String resumptionToken) throws OaiPmhException;

    OaiPmhResponse<ListRecords> listRecords(final String metadataPrefix, final Instant from, final Instant until,
            final String set) throws OaiPmhException;

    OaiPmhResponse<ListRecords> listRecords(final String resumptionToken) throws OaiPmhException;

    OaiPmhResponse<GetRecord> getRecord(final String metadataPrefix, final URI identifier) throws OaiPmhException;
}
