package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

//import org.eclipse.microprofile.jwt.JsonWebToken;
import javax.inject.Inject;

@Path("/frontend")
public class GreetingResource {

    private static Logger log = Logger.getLogger(GreetingResource.class);

    //@Inject
    //JsonWebToken jwt;

    @Inject
    @RestClient
    IBackend backend;

    @GET
    public Uni<String> frontend() {
        log.info("frontend()");
        return backend.invokeBackend();
    }
}
