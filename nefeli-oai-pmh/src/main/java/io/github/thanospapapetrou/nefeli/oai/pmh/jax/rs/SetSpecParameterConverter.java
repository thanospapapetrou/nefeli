package io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.ParamConverter;

import org.openarchives.oai._2.SetSpec;

@ApplicationScoped
public class SetSpecParameterConverter implements ParamConverter<SetSpec> {
    @Override
    public SetSpec fromString(final String string) {
        return (string == null) ? null : new SetSpec(string);
    }

    @Override
    public String toString(final SetSpec setSpec) {
        return (setSpec == null) ? null : setSpec.toString();
    }
}
