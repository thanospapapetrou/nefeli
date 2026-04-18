package io.github.thanospapapetrou.nefeli.containers.toolkit.jaxb;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;

import edu.vt.dlib.oai.oai.metadata.toolkit.Toolkit;
import io.github.thanospapapetrou.nefeli.oai.pmh.ContainerAdapter;

public class ToolkitAdapter extends ContainerAdapter<Toolkit> {
    @Inject
    public ToolkitAdapter(final DocumentBuilder builder,
            @Named("toolkitMarshaller") final Marshaller marshaller,
            @Named("toolkitUnmarshaller") final Unmarshaller unmarshaller) {
        super(builder, marshaller, unmarshaller, Toolkit.class);
    }
}
