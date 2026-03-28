package io.github.thanospapapetrou.nefeli.web;

import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhServer;
import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhParameterConverterProvider;
import io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs.OaiPmhWriter;

@ApplicationPath("/")
public class Nefeli extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(OaiPmhParameterConverterProvider.class, OaiPmhServer.class, OaiPmhWriter.class);
    }
}
