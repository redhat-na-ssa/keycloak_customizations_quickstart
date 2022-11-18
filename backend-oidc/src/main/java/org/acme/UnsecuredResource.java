package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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
