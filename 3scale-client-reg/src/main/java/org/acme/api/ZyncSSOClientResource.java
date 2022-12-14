package org.acme.api;

import org.acme.ISSOClientRegistration;
import org.acme.model.Client;
import org.acme.model.OIDC;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Path("")
@ApplicationScoped
public class ZyncSSOClientResource {

    @Inject
    ISSOClientRegistration ssoClientReg;

    @DELETE
    @Path("/clients/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client deleteClient( @PathParam("clientId") String clientId ) {
      return ssoClientReg.deleteClient(clientId);
    }

    @GET
    @Path("/clients/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client readClient( @PathParam("clientId") String clientId ){
      return ssoClientReg.readClient(clientId);
    }

    /**
       Example:
          curl -X PUT localhost:2080/clients/12345 \
            -H 'Content-Type: application/json' \
            -d '{"client_id":"12345","client_secret":"23567"}'
     */
    @PUT
    @Path("/clients/{clientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client saveClient(@PathParam("clientId") String clientId, Client client ){
      return ssoClientReg.saveClient(client);
    }

    @GET
    @Path("/.well-known/openid-configuration")
    @Produces(MediaType.APPLICATION_JSON)
    public OIDC getWellKnownOpenidConfiguration(){
      return ssoClientReg.getWellKnownOpenidConfiguration();
    }

    @GET
    @Path("/sanityCheck")
    @Produces({"text/plain"})
    public String sanityCheck() {
      return "good-to-go";
    }

}
