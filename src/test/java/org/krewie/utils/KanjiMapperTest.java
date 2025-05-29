package org.krewie.utils;

import org.junit.jupiter.api.Test;
import org.krewie.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KanjiMapperTest {

    private final Kanji sampleKanji = new Kanji(
            1,
            "学",
            List.of("study", "learning"),
            "ancient form of child in a house",
            List.of("ガク"),
            List.of("まなぶ"),
            "Education",
            List.of(new SampleWord("学生", "がくせい", "student")),
            8,
            5,
            123,
            "子",
            List.of("冖", "子")
    );

    @Test
    void toEntity_shouldConvertCorrectly() {
        KanjiEntity entity = KanjiMapper.toEntity(sampleKanji);

        assertEquals(1, entity.serialNumber);
        assertEquals("学", entity.kanji);
        assertEquals(List.of("study", "learning"), entity.meaning);
        assertEquals("ancient form of child in a house", entity.origin);
        assertEquals(List.of("ガク"), entity.onReading);
        assertEquals(List.of("まなぶ"), entity.kunReading);
        assertEquals("Education", entity.header);
        assertEquals(8, entity.strokeNumber);
        assertEquals(5, entity.jlptGrade);
        assertEquals(123, entity.frequency);
        assertEquals("子", entity.radical);
        assertEquals(List.of("冖", "子"), entity.components);

        assertNotNull(entity.sampleWords);
        assertEquals(1, entity.sampleWords.size());
        assertEquals("学生", entity.sampleWords.getFirst().kanji);
    }

    @Test
    void fromEntity_shouldConvertBackCorrectly() {
        KanjiEntity entity = KanjiMapper.toEntity(sampleKanji);
        Kanji kanji = KanjiMapper.fromEntity(entity);

        assertEquals(sampleKanji.serial_number(), kanji.serial_number());
        assertEquals(sampleKanji.kanji(), kanji.kanji());
        assertEquals(sampleKanji.meaning(), kanji.meaning());
        assertEquals(sampleKanji.origin(), kanji.origin());
        assertEquals(sampleKanji.on_reading(), kanji.on_reading());
        assertEquals(sampleKanji.kun_reading(), kanji.kun_reading());
        assertEquals(sampleKanji.header(), kanji.header());
        assertEquals(sampleKanji.sample_words(), kanji.sample_words());
        assertEquals(sampleKanji.stroke_number(), kanji.stroke_number());
        assertEquals(sampleKanji.jlpt_grade(), kanji.jlpt_grade());
        assertEquals(sampleKanji.frequency(), kanji.frequency());
        assertEquals(sampleKanji.radical(), kanji.radical());
        assertEquals(sampleKanji.components(), kanji.components());
    }

    @Test
    void toEntity_shouldHandleNullSampleWords() {
        Kanji input = new Kanji(
                2, "愛", List.of("love"), "pictogram",
                List.of("アイ"), List.of("いとしい"),
                "Emotion", null, 13, 4, 456,
                "心", List.of("爫", "冖", "心")
        );

        KanjiEntity entity = KanjiMapper.toEntity(input);

        assertEquals(entity.sampleWords, List.of());
    }

    @Test
    void toEntity_shouldHandleEmptyFields() {
        Kanji input = new Kanji(
                3, "", List.of(), "", List.of(), List.of(),
                "", List.of(), 0, 0, 0, "", List.of()
        );

        KanjiEntity entity = KanjiMapper.toEntity(input);

        assertNotNull(entity);
        assertEquals(3, entity.serialNumber);
        assertTrue(entity.meaning.isEmpty());
        assertTrue(entity.onReading.isEmpty());
        assertTrue(entity.kunReading.isEmpty());
        assertTrue(entity.components.isEmpty());
        assertEquals("", entity.kanji);
    }

    @Test
    void fromEntity_shouldHandleNullSampleWords() {
        KanjiEntity entity = new KanjiEntity();
        entity.serialNumber = 99;
        entity.kanji = "例";
        entity.meaning = List.of("example");
        entity.sampleWords = null;
        entity.onReading = List.of("レイ");
        entity.kunReading = List.of("たとえる");
        entity.header = "Header";
        entity.origin = "sample";
        entity.strokeNumber = 10;
        entity.jlptGrade = 3;
        entity.frequency = 300;
        entity.radical = "口";
        entity.components = List.of("口", "力");

        Kanji result = KanjiMapper.fromEntity(entity);

        assertEquals(99, result.serial_number());
        assertEquals("例", result.kanji());
        assertEquals(result.sample_words(), List.of());
    }
}
