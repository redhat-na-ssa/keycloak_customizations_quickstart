package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.inject.Inject;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import org.jboss.resteasy.annotations.cache.NoCache;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/hello")
public class GreetingResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("user")
    @NoCache
    public String hello() {
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
