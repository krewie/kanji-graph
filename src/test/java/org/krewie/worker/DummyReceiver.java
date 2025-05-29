package org.krewie.worker;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.krewie.model.Kanji;

import java.util.ArrayList;
import java.util.List;

@Path("/dummy")
@Consumes(MediaType.APPLICATION_JSON)
public class DummyReceiver {

    public static final List<Kanji> received = new ArrayList<>();

    @POST
    @Path("/kanji")
    public void receiveSingle(Kanji kanji) {
        received.add(kanji);
    }

    @POST
    @Path("/kanji/list")
    public void receiveList(List<Kanji> list) {
        received.addAll(list);
    }

    public static void reset() {
        received.clear();
    }
}
