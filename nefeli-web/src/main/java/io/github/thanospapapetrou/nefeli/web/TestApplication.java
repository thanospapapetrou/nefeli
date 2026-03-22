package io.github.thanospapapetrou.nefeli.web;

import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import io.github.thanospapapetrou.nefeli.oai.pmh.OaiPmhServer;

@ApplicationPath("/")
public class TestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(OaiPmhServer.class, Test.class);
    }
}
