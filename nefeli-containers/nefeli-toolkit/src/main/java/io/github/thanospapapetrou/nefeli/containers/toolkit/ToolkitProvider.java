package io.github.thanospapapetrou.nefeli.containers.toolkit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;
import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

@ApplicationScoped
public class ToolkitProvider extends ContainerProvider<Toolkit> {
    @Inject
    public ToolkitProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Toolkit.class);
    }
}
