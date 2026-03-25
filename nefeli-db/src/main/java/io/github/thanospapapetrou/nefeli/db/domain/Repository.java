package io.github.thanospapapetrou.nefeli.db.domain;

import java.net.URL;
import java.time.Instant;
import java.util.List;

import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import org.openarchives.oai._2.DeletedRecord;
import org.openarchives.oai._2.Granularity;

import io.github.thanospapapetrou.nefeli.db.jpa.InternetAddressConverter;

@Entity
@Table(name = "\"REPOSITORY\"")
public class Repository implements Comparable<Repository> {
    @Column(name = "\"URL\"")
    @Id
    private final URL url;
    @Column(name = "\"UPDATED\"")
    private final Instant updated;
    @Column(name = "\"ERROR\"")
    private final String error;
    @Column(name = "\"NAME\"")
    private final String name;
    @CollectionTable(name = "\"REPOSITORY_ADMINS\"", joinColumns = @JoinColumn(name = "\"URL\""))
    @Column(name = "\"ADMIN\"")
    @Convert(converter = InternetAddressConverter.class)
    @ElementCollection
    private final List<InternetAddress> admins;
    @Column(name = "\"EARLIEST\"")
    private final Instant earliest;
    @Column(name = "\"DELETED\"")
    @Enumerated(EnumType.ORDINAL)
    private final DeletedRecord deleted;
    @Column(name = "\"GRANULARITY\"")
    @Enumerated(EnumType.ORDINAL)
    private final Granularity granularity;
    @CollectionTable(name = "\"REPOSITORY_COMPRESSIONS\"", joinColumns = @JoinColumn(name = "\"URL\""))
    @Column(name = "\"COMPRESSION\"")
    @ElementCollection
    private final List<String> compressions;
    // TODO descriptions

    public Repository(final URL url, final Instant updated, final String error, final String name,
            final List<InternetAddress> admins,
            final Instant earliest, final DeletedRecord deleted, final Granularity granularity,
            final List<String> compressions) {
        this.url = url;
        this.updated = updated;
        this.error = error;
        this.name = name;
        this.admins = admins;
        this.earliest = earliest;
        this.deleted = deleted;
        this.granularity = granularity;
        this.compressions = compressions;
    }

    private Repository() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public URL getUrl() {
        return url;
    }

    public Instant getUpdated() {
        return updated;
    }

    public String getError() {
        return error;
    }

    public String getName() {
        return name;
    }

    public List<InternetAddress> getAdmins() {
        return admins;
    }

    public Instant getEarliest() {
        return earliest;
    }

    public DeletedRecord getDeleted() {
        return deleted;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public List<String> getCompressions() {
        return compressions;
    }

    @Override
    public int compareTo(final Repository repository) {
        return url.toString().compareTo(repository.url.toString());
    }

    @Override
    public boolean equals(final Object object) {
        return (object instanceof Repository) && (compareTo((Repository) object) == 0);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
