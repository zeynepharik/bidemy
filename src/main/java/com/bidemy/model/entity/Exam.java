package com.bidemy.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Exam{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private String description;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Question> questionsList;

}
