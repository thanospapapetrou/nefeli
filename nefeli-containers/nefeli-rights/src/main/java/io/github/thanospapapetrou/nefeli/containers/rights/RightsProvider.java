package io.github.thanospapapetrou.nefeli.containers.rights;

import org.openarchives.oai._2_0.rights.Rights;

import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class RightsProvider extends ContainerProvider<Rights> {
    public RightsProvider() {
        super(Rights.class);
    }
}
