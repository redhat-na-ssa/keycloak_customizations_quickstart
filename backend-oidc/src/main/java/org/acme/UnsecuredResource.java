package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map.Entry;

import io.smallrye.mutiny.Uni;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

@Path("/unsecured")
public class UnsecuredResource {

    @Context
    HttpServerRequest request;

    @GET
    @Path("/sanityCheck")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> sanityCheck() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode top = mapper.createObjectNode().put("message", "Good to go!");
        ArrayNode hNodeArray = top.putArray("headers");
        
        MultiMap headers = request.headers();
        for(Entry<String, String> entry : headers.entries()){
            ObjectNode element = mapper.createObjectNode().put(entry.getKey(), entry.getValue());
            hNodeArray.add(element);
        }

        //String rString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(top);
        String rString = mapper.writeValueAsString(top);

        Response eRes = Response.status(Response.Status.OK).entity(rString).build();
        return Uni.createFrom().item(eRes);
    }
}
