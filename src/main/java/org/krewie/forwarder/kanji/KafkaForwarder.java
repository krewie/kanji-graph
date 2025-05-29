package org.krewie.forwarder.kanji;

import jakarta.enterprise.context.ApplicationScoped;
import org.krewie.model.Kanji;
import org.krewie.model.KanjiEntity;

import java.util.List;

@ApplicationScoped
public class KafkaForwarder implements Forwarder {

    @Override
    public void forward(KanjiEntity kanji) {
        // TODO: simulate kafka logic
        System.out.println("Forwarding to kafka: " + kanji.kanji);
    }

    @Override
    public void forward(List<KanjiEntity> kanjiList) {
        // TODO: Instead of below, actually try to BULK send to kafka
        // TODO: SO we get 1 transaction even for 20 or 30 kanjis
        kanjiList.forEach(this::forward);
    }
}
