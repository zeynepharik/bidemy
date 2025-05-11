package com.bidemy.model.entity;

import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "curriculum_type")
public class CurriculumItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
