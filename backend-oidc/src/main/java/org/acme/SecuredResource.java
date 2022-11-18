package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.Set;

import javax.annotation.security.RolesAllowed;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;

@Authenticated
@Path("/secured")
public class SecuredResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Context
    HttpServerRequest request;

    @GET
    @Path("/authNonly")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> authNonly() {
        StringBuilder sBuilder = new StringBuilder();
        String userName = securityIdentity.getPrincipal().getName();
        sBuilder.append("Hello ");
        sBuilder.append(userName);

        /*
        sBuilder.append("\nRequest Headers: ");
        MultiMap headers = request.headers();
        for(Entry<String, String> entry : headers.entries()){
            sBuilder.append("\n\t"+entry.getKey()+"\t"+entry.getValue());
        }*/

        Response eRes = Response.status(Response.Status.OK).entity(sBuilder.toString()).build();
        return Uni.createFrom().item(eRes);
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ldap-user")          // App based RBAC (as opposed to centralized UMA based Authorization Services provided by Keycloak)
    @Path("/roles-required")
    public Uni<Response> rolesRequired() {
        String userName = securityIdentity.getPrincipal().getName();
        Set<String> roles = securityIdentity.getRoles();
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Hello ");
        sBuilder.append(userName);
        sBuilder.append(" with roles: ");
        for(String role: roles){
            sBuilder.append(role);
            sBuilder.append(" ");
        }

        Response eRes = Response.status(Response.Status.OK).entity(sBuilder.toString()).build();
        return Uni.createFrom().item(eRes);
    }
}
