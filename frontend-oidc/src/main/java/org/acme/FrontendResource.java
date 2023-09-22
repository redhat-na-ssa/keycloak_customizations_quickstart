package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

//import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

@Path("/")
public class FrontendResource {

    private static Logger log = Logger.getLogger(FrontendResource.class);

    //@Inject
    //JsonWebToken jwt;

    @Inject
    @RestClient
    IBackend backend;

    @GET
    @Path("/sanityCheck")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> sanityCheck() {
        log.info("sanityCheck()");
        return backend.sanityCheck()
        .onFailure().recoverWithItem(f -> {
          Response eRes = Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(f.getMessage()).build();
          return eRes;
        });
    }


    @GET
    @Path("/authNonly")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> authNonly() {
        log.info("authNonly()");
        return backend.authNonly()
          .onFailure().recoverWithItem(f -> {
            Response eRes = Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(f.getMessage()).build();
            return eRes;
          });

    }

    @GET
    @Path("/roles-required")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> rolesRequired() {
        log.info("rolesRequired()");
        return backend.rolesRequired()
        .onFailure().recoverWithItem(f -> {
          Response eRes = Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(f.getMessage()).build();
          return eRes;
        });
    }
}
