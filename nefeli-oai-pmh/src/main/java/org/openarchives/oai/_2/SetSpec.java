package org.openarchives.oai._2;

public class SetSpec {
    private static final String DELIMITER = ":";

    private final SetSpec parent;
    private final String name;

    public SetSpec(final String spec) {
        this((spec.lastIndexOf(DELIMITER) == -1) ? null : new SetSpec(spec.substring(0, spec.lastIndexOf(DELIMITER))),
                (spec.lastIndexOf(DELIMITER) == -1) ? spec : spec.substring(spec.lastIndexOf(DELIMITER) + 1));
    }

    private SetSpec(final SetSpec parent, final String name) {
        this.parent = parent;
        this.name = name;
    }

    public SetSpec getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ((parent == null) ? "" : parent + DELIMITER) + name;
    }
}
