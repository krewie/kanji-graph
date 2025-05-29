package org.krewie.worker;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.krewie.model.Kanji;
import org.krewie.model.SampleWord;
import org.krewie.repository.KanjiEntityRepository;
import org.krewie.utils.KanjiMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DispatchWorkerTest {

    @Inject
    KanjiEntityRepository repository;

    @Inject
    DispatchWorker dispatchWorker;

    @BeforeEach
    @Transactional
    void setUp() {
        DummyReceiver.reset();
        repository.deleteAll();
    }

    @Test
    @Transactional
    void testDispatchSendsPendingKanjis() {
        //TODO: Skapa util builder for de har
        List<Kanji> kanjiList = List.of(
                new Kanji(
                        1,
                        "学",
                        List.of("study"),
                        "origin",
                        List.of("ガク"),
                        List.of("まなぶ"),
                        "header",
                        List.of(
                                new SampleWord(
                                        "学生",
                                        "がくせい",
                                        "student")
                        ),
                        8,
                        5,
                        123,
                        "子",
                        List.of("冖", "子")
                ),
                new Kanji(
                        2,
                        "愛",
                        List.of("love"),
                        "origin",
                        List.of("アイ"),
                        List.of("いとしい"),
                        "header",
                        List.of(),
                        13,
                        4,
                        456,
                        "心",
                        List.of("爫", "冖", "心")
                )
        );

        kanjiList.forEach(k -> repository.persist(KanjiMapper.toEntity(k)));

        //Manually trigger the worker
        dispatchWorker.sendPendingKanji();

        assertEquals(4, DummyReceiver.received.size());

        long gakuCount = DummyReceiver.received.stream().filter(k -> k.kanji().equals("学")).count();
        long aiCount = DummyReceiver.received.stream().filter(k -> k.kanji().equals("愛")).count();

        assertEquals(2, gakuCount); // once to mongo, once to neo4j
        assertEquals(2, aiCount);   // once to mongo, once to neo4j
}
}