package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("/backend")
@RegisterRestClient
public interface IBackend {

    @GET
    Uni<String> invokeBackend();
    
}
