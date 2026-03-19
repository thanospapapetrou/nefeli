package io.github.thanospapapetrou.nefeli.db;

import java.net.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REPOSITORY")
public class Repository implements Comparable<Repository> {
    @Column(name = "URL")
    @Id
    private final URL url;

    @Column(name = "NAME")
    private final String name;

    public Repository(final URL url, final String name) {
        this.url = url;
        this.name = name;
    }

    private Repository() {
        this(null, null);
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
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
