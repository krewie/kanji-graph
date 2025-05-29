package org.krewie.utils;

import org.krewie.model.Kanji;
import org.krewie.model.KanjiEntity;
import org.krewie.model.SampleWord;
import org.krewie.model.SampleWordEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KanjiMapper {

    public static KanjiEntity toEntity(Kanji kanji) {
        KanjiEntity entity = new KanjiEntity();
        entity.serialNumber = kanji.serial_number();
        entity.kanji = kanji.kanji();
        entity.meaning = kanji.meaning();
        entity.origin = kanji.origin();
        entity.onReading = kanji.on_reading();
        entity.kunReading = kanji.kun_reading();
        entity.header = kanji.header();
        entity.sampleWords = toSampleWordEntities(kanji.sample_words());
        entity.strokeNumber = kanji.stroke_number();
        entity.jlptGrade = kanji.jlpt_grade();
        entity.frequency = kanji.frequency();
        entity.radical = kanji.radical();
        entity.components = kanji.components();
        entity.sent = false;
        return entity;
    }

    public static Kanji fromEntity(KanjiEntity kanjiEntity) {
        return new Kanji(
                kanjiEntity.serialNumber,
                kanjiEntity.kanji,
                kanjiEntity.meaning,
                kanjiEntity.origin,
                kanjiEntity.onReading,
                kanjiEntity.kunReading,
                kanjiEntity.header,
                toSampleWords(kanjiEntity.sampleWords),
                kanjiEntity.strokeNumber,
                kanjiEntity.jlptGrade,
                kanjiEntity.frequency,
                kanjiEntity.radical,
                kanjiEntity.components
        );

    }

    private static List<SampleWord> toSampleWords(List<SampleWordEntity> sampleWords) {
        return Optional.ofNullable(sampleWords).orElse(List.of()).stream()
                .map(sampleWordEntity -> new SampleWord(
                        sampleWordEntity.kanji,
                        sampleWordEntity.reading,
                        sampleWordEntity.meaning
                )).toList();
    }

    private static List<SampleWordEntity> toSampleWordEntities(List<SampleWord> sampleWords) {
        return Optional.ofNullable(sampleWords).orElse(List.of()).stream()
                .map(sw -> {
                    SampleWordEntity swe = new SampleWordEntity();
                    swe.kanji = sw.kanji();
                    swe.reading = sw.reading();
                    swe.meaning = sw.meaning();
                    return swe;
                })
                .collect(Collectors.toList());
    }

}
