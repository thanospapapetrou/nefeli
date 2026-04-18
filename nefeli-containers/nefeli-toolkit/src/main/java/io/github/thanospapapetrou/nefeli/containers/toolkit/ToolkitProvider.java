package io.github.thanospapapetrou.nefeli.containers.toolkit;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;
import io.github.thanospapapetrou.nefeli.containers.toolkit.jaxb.ToolkitAdapter;

public class ToolkitProvider extends ContainerProvider<Toolkit> {
    public ToolkitProvider() {
        super(ToolkitAdapter.class);
    }
}
