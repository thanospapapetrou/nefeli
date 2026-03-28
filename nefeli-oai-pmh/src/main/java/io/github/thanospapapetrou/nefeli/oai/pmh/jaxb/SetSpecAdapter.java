package io.github.thanospapapetrou.nefeli.oai.pmh.jaxb;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2.SetSpec;

@ApplicationScoped
public class SetSpecAdapter extends XmlAdapter<String, SetSpec> {
    @Override
    public String marshal(final SetSpec setSpec) throws Exception {
        return (setSpec == null) ? null : setSpec.toString();
    }

    @Override
    public SetSpec unmarshal(final String string) throws Exception {
        return (string == null) ? null : new SetSpec(string);
    }
}
