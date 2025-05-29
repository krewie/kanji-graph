package org.krewie.forwarder.kanji;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.krewie.forwarder.rest.MongoClient;
import org.krewie.forwarder.rest.Neo4jClient;
import org.krewie.model.Kanji;
import org.krewie.model.KanjiEntity;
import org.krewie.repository.KanjiEntityRepository;
import org.krewie.utils.KanjiMapper;

import java.util.List;

@ApplicationScoped
public class RestForwarder implements Forwarder {

    @Inject
    @RestClient
    MongoClient mongoClient;

    @Inject
    @RestClient
    Neo4jClient neo4jClient;

    @Inject
    KanjiEntityRepository kanjiEntityRepository;

    @Override
    public void forward(KanjiEntity kanji) {
        mongoClient.sendSingle(KanjiMapper.fromEntity(kanji));
        neo4jClient.sendSingle(KanjiMapper.fromEntity(kanji));
        // TODO: when both are transactional mark as complete
        kanjiEntityRepository.markAsSent(List.of(kanji.id));
    }

    @Override
    public void forward(List<KanjiEntity> kanjiList) {
        mongoClient.sendList(kanjiList.stream().map(KanjiMapper::fromEntity).toList());
        neo4jClient.sendList(kanjiList.stream().map(KanjiMapper::fromEntity).toList());
    }
}
