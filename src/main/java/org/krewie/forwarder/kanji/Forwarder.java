package org.krewie.forwarder.kanji;

import org.krewie.model.Kanji;
import org.krewie.model.KanjiEntity;

import java.util.List;

public interface Forwarder {
    boolean forward(KanjiEntity kanji);
    boolean forward(List<KanjiEntity> kanjiList);
}
