package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey="iBackend")
@RegisterClientHeaders
@Path("/backend")
public interface IBackend {

    @GET
    @Path("/headers")
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> invokeBackend();
    
}
