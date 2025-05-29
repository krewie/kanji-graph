package org.krewie.forwarder.kanji;

import org.krewie.model.Kanji;
import org.krewie.model.KanjiEntity;

import java.util.List;

public interface Forwarder {
    void forward(KanjiEntity kanji);
    void forward(List<KanjiEntity> kanjiList);
}
