package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.mutiny.Uni;

@Path("/vault")
public class VaultTest {

    @ConfigProperty(name = "database_name")
    String database_name;

    @GET
    @Path("/database-name")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> dbName() {
        Response eRes = Response.status(Response.Status.OK).entity(database_name).build();
        return Uni.createFrom().item(eRes);
    }
    
}
