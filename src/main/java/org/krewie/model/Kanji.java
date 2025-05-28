// KanjiEntry.java
package org.krewie.model;

import java.util.List;

public record Kanji(
        int serial_number,
        String kanji,
        List<String> meaning,
        String origin,
        List<String> on_reading,
        List<String> kun_reading,
        String header,
        List<SampleWord> sample_words,
        int stroke_number,
        int jlpt_grade,
        int frequency,
        String radical,
        List<String> components
) {}
