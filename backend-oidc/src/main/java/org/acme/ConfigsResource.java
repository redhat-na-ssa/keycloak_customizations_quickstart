package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.reactive.datasource.ReactiveDataSource;
import io.smallrye.mutiny.Uni;

@Path("/configs")
public class ConfigsResource {

    @ConfigProperty(name = "database_name", defaultValue = "rhbk")
    String database_name;

    @ConfigProperty(name = "username", defaultValue = "rhbk")
    String user_name;

    @Inject
    @ReactiveDataSource("pgsso")
    io.vertx.mutiny.pgclient.PgPool pgClient;

    @GET
    @Path("/database-name")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> dbName() {
        Response eRes = Response.status(Response.Status.OK).entity(database_name).build();
        return Uni.createFrom().item(eRes);
    }
    
    @GET
    @Path("/user-name")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> userName() {
        Response eRes = Response.status(Response.Status.OK).entity(user_name).build();
        return Uni.createFrom().item(eRes);
    }

    @GET
    @Path("sso-db-table-count")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> ssoDBTableCount() {

        return pgClient.query("SELECT * FROM information_schema.tables;").execute()
        .onItem().transformToUni(uRowSet -> {
            int rowCount = uRowSet.size();
            Response eRes = Response.status(Response.Status.OK).entity(rowCount).build();
            return Uni.createFrom().item(eRes);
        })
        .onFailure().recoverWithItem(f -> {
            Response eRes = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(f.getMessage()).build();
            return eRes;
        });
    }
}
