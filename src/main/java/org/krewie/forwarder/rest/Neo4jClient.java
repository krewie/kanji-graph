package org.krewie.forwarder.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.krewie.model.Kanji;

import java.util.List;

@RegisterRestClient(configKey = "kanji-api-neo4j")
@Path("/kanji")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Neo4jClient {

    @POST
    void sendSingle(Kanji kanji);

    @POST
    @Path("/list") void sendList(List<Kanji> kanjiList);
}
