package org.krewie.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.krewie.model.Kanji;
import org.krewie.repository.KanjiEntityRepository;
import org.krewie.utils.KanjiMapper;

import java.util.List;

@ApplicationScoped
public class KanjiService {

    private static final Logger logger = Logger.getLogger(KanjiService.class);

    @Inject
    KanjiEntityRepository repository;

    public void save(Kanji kanji) {
        repository.save(KanjiMapper.toEntity(kanji));
        //getForwarder().forward(kanji);
    }

    public void saveAll(List<Kanji> kanjiList) {
        repository.saveAll(kanjiList.stream().map(KanjiMapper::toEntity).toList());
        //getForwarder().forward(kanjiList);
    }
}
