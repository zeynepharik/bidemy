package com.bidemy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(
            name = "section_id"
    )
    private Section section;
    @OneToMany(
            mappedBy = "lesson",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<Exam> exams = new ArrayList();
    @OneToOne(
            mappedBy = "lesson",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private ArticleContent articleContent;

    @OneToOne(
            mappedBy = "lesson",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private VideoContent videoContent;
}