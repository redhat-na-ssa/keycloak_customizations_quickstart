package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.jwt.JsonWebToken;
import javax.inject.Inject;

@Path("/frontend")
public class GreetingResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    @RestClient
    IBackend backend;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> frontend() {
        return backend.invokeBackend();
    }
}
