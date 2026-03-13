package io.github.thanospapapetrou.nefeli.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

@Path("/world")
public class Hello {
    @GET
    @Path("/world")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World!";
    }
}
