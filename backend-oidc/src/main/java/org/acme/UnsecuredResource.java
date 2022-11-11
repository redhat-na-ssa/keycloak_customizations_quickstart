package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;

import java.util.Map.Entry;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

@Path("/unsecured")
public class UnsecuredResource {

    @Context
    HttpServerRequest request;

    @GET
    @Path("/sanityCheck")
    @Produces(MediaType.TEXT_PLAIN)
    public String sanityCheck() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Good to go !");

        sBuilder.append("\nRequest Headers: ");
        MultiMap headers = request.headers();
        for(Entry<String, String> entry : headers.entries()){
            sBuilder.append("\n\t"+entry.getKey()+"\t"+entry.getValue());
        }

        return sBuilder.toString();
    }
}
