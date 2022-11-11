package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;

//import org.eclipse.microprofile.jwt.JsonWebToken;
import javax.inject.Inject;

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
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> sanityCheck() {
        log.info("sanityCheck()");
        return backend.sanityCheck();
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
