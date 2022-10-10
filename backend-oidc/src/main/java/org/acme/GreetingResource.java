package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.core.Context;

import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.security.RolesAllowed;
import org.jboss.resteasy.annotations.cache.NoCache;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

@Path("/backend")
public class GreetingResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Context
    HttpServerRequest request;

    @GET
    @Path("/headers")
    @Produces(MediaType.TEXT_PLAIN)
    public String echoHeaders() {
        MultiMap headers = request.headers();
        StringBuilder sBuilder = new StringBuilder("Request Headers: ");
        for(Entry<String, String> entry : headers.entries()){
            sBuilder.append("\n\t"+entry.getKey()+"\t"+entry.getValue());
        }
        return sBuilder.toString();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ldap-user")          // App based RBAC (as opposed to centralized UMA based Authorization Services provided by Keycloak)
    @NoCache
    @Path("/secured")
    public String secured() {
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
        
        return sBuilder.toString();
    }
}
