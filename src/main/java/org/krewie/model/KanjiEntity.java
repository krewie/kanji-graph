package org.krewie.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "kanji")
public class KanjiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public int serialNumber;

    public String kanji;

    @ElementCollection
    public List<String> meaning;

    @Lob
    public String origin;

    @ElementCollection
    public List<String> onReading;

    @ElementCollection
    public List<String> kunReading;

    public String header;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<SampleWordEntity> sampleWords;

    public int strokeNumber;

    public int jlptGrade;

    public int frequency;

    public String radical;

    @ElementCollection
    public List<String> components;

    public boolean sent = false;  // for your "pending" idea

    // Add getters/setters or make fields public
    // Add no-arg constructor
}
