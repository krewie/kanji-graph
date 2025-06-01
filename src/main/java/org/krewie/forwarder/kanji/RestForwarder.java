package org.krewie.forwarder.kanji;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.krewie.forwarder.rest.MongoClient;
import org.krewie.forwarder.rest.Neo4jClient;
import org.krewie.model.KanjiEntity;
import org.krewie.utils.KanjiMapper;

import java.util.List;

@ApplicationScoped
public class RestForwarder implements Forwarder {

    private static final Logger logger = Logger.getLogger(RestForwarder.class);

    @Inject
    @RestClient
    MongoClient mongoClient;

    @Inject
    @RestClient
    Neo4jClient neo4jClient;

    @Override
    public boolean forward(KanjiEntity kanji) {
        mongoClient.sendSingle(KanjiMapper.fromEntity(kanji));
        neo4jClient.sendSingle(KanjiMapper.fromEntity(kanji));

        return false;
        // TODO: Should return true on success, false otherwise.
    }

    @Override
    public boolean forward(List<KanjiEntity> kanjiList) {
        try {
            mongoClient.sendList(kanjiList.stream().map(KanjiMapper::fromEntity).toList());
            neo4jClient.sendList(kanjiList.stream().map(KanjiMapper::fromEntity).toList());
        } catch (Exception e) {
            logger.errorf("Failed to forward %d kanji using REST forwarder", kanjiList.size());
        }

        return true;

        // TODO: Should return true on success, false otherwise.
    }
}
