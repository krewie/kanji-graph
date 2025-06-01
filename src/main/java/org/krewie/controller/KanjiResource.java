package org.krewie.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.krewie.model.Kanji;
import org.krewie.service.KanjiService;

import java.util.List;

@Path("/kanji")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KanjiResource {

    @Inject
    KanjiService service;

    @POST
    @Path("/list")
    public void saveKanjiList(List<Kanji> kanjiList) {
        service.saveAll(kanjiList);
    }

    @POST
    public void saveKanji(Kanji kanji) {
        service.save(kanji);
    }

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

    // Exception handling, should be moved outside when time...
    @ServerExceptionMapper
    public RestResponse<String> handleIllegalArgument(IllegalArgumentException e) {
        return RestResponse.status(Response.Status.BAD_REQUEST, "Bad request: " + e.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<String> handleNullPointer(NullPointerException e) {
        return RestResponse.status(Response.Status.BAD_REQUEST, "Missing or null payload");
    }

    @ServerExceptionMapper
    public RestResponse<String> handleGeneric(Exception e) {
        return RestResponse.status(Response.Status.BAD_REQUEST, "Invalid request: " + e.getClass().getSimpleName());
    }
}
