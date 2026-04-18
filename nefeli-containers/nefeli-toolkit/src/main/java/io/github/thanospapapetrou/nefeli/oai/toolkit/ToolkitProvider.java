package io.github.thanospapapetrou.nefeli.oai.toolkit;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;
import io.github.thanospapapetrou.nefeli.oai.pmh.XmlContentProvider;
import io.github.thanospapapetrou.nefeli.oai.toolkit.jaxb.ToolkitAdapter;

public class ToolkitProvider extends XmlContentProvider<Toolkit> {
    public ToolkitProvider() {
        super(ToolkitAdapter.class);
    }
}
