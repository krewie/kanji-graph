package org.krewie.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    @Path("list")
    @Transactional
    public void saveKanjiList(List<Kanji> kanjiList) {
        service.saveAll(kanjiList);
    }

    @POST
    @Transactional
    public void saveKanji(Kanji kanji) {
        service.save(kanji);
    }

    @GET
    public List<Kanji> getAll() {
        return List.of();
    }
}
