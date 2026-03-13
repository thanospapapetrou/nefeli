package io.github.thanospapapetrou.nefeli.web;

import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/hello")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(Hello.class);
    }
}
