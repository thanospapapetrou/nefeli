package io.github.thanospapapetrou.nefeli.containers.toolkit;

import jakarta.enterprise.inject.spi.CDI;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;
import io.github.thanospapapetrou.nefeli.common.jaxb.JaxbHelper;
import io.github.thanospapapetrou.nefeli.common.xml.XmlHelper;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;

public class ToolkitProvider extends ContainerProvider<Toolkit> {
    public ToolkitProvider() {
        this(CDI.current().select(XmlHelper.class).get(), CDI.current().select(JaxbHelper.class).get());
    }

    private ToolkitProvider(final XmlHelper xml, final JaxbHelper jaxb) {
        super(xml, jaxb, Toolkit.class);
    }
}
