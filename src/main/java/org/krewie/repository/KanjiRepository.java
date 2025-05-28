package org.krewie.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.krewie.model.Kanji;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class KanjiRepository {

    private List<Kanji> kanjiList;

    public KanjiRepository() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass().getResourceAsStream("/kanji-data.json")) {
            kanjiList = mapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load local Kanji data");
        }
    }

    public List<Kanji> findAll() {
        return kanjiList;
    }

    public Kanji findByCharacter(String character) {
        return kanjiList.stream()
                .filter(k -> k.kanji().equals(character))
                .findFirst()
                .orElse(null);
    }
}
