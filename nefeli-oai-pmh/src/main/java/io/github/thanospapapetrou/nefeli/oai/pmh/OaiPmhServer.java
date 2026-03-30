package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.internet.InternetAddress;

import org.openarchives.oai._2.DeletedRecord;
import org.openarchives.oai._2.Granularity;
import org.openarchives.oai._2.Header;
import org.openarchives.oai._2.ListIdentifiers;
import org.openarchives.oai._2.ListRecords;
import org.openarchives.oai._2.ListSets;
import org.openarchives.oai._2.Metadata;
import org.openarchives.oai._2.MetadataFormat;
import org.openarchives.oai._2.Record;
import org.openarchives.oai._2.SetSpec;

import io.github.thanospapapetrou.nefeli.common.Configuration;
import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhParameterConverterProvider;

@ApplicationScoped
public class OaiPmhServer extends AbstractOaiPmhServer {
    @Inject
    public OaiPmhServer(final Clock clock, final OaiPmhParameterConverterProvider provider,
            @Configuration.Property("nefeli.oai-pmh.server.repositoryName") final String repositoryName,
            @Configuration.Property("nefeli.oai-pmh.server.adminEmails") final List<InternetAddress> adminEmails,
            @Configuration.Property("nefeli.oai-pmh.server.deletedRecord") final DeletedRecord deletedRecord,
            @Configuration.Property("nefeli.oai-pmh.server.granularity") final Granularity granularity,
            @Configuration.Property("nefeli.oai-pmh.server.compressions") final List<String> compressions) {
        super(clock, provider, repositoryName, adminEmails, deletedRecord, granularity, compressions);
    }

    OaiPmhServer() {
        this(null, null, null, null, null, null, null);
    }

    @Override
    protected Instant getEarliestDatestamp() {
        return Instant.EPOCH; // TODO
    }

    @Override

    protected List<MetadataFormat> listMetadataFormats(final Instant datestamp) {
        return List.of(); // TODO
    }

    @Override
    protected ListSets listSets(final Instant datestamp, final String resumptionToken) throws OaiPmhException {
        return new ListSets(List.of(), null); // TODO
    }

    @Override
    protected ListIdentifiers listIdentifiers(final Instant datestamp, final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) {
        return new ListIdentifiers(List.of(), null); // TODO
    }

    @Override
    protected ListIdentifiers listIdentifiers(final Instant datestamp, final String resumptionToken) {
        return new ListIdentifiers(List.of(), null); // TODO
    }

    @Override
    protected ListRecords listRecords(final Instant datestamp, final String metadataPrefix, final Instant from,
            final Instant until, final SetSpec set) {
        return new ListRecords(List.of(), null); // TODO
    }

    @Override
    protected ListRecords listRecords(final Instant datestamp, final String resumptionToken) {
        return new ListRecords(List.of(), null); // TODO
    }

    @Override
    protected Record getRecord(final Instant datestamp, final String metadataPrefix, final URI identifier) {
        return new Record(new Header(identifier, datestamp, List.of(), false), new Metadata(null), List.of()); // TODO
    }
}
