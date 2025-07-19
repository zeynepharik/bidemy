package com.bidemy.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class LessonRequest {

    @NotBlank(message = "Lesson title is required")
    private String title;

    private Long sectionId;

    @Valid
    private List<ExamRequest> exams = new ArrayList<>();

    private VideoContentRequest videoContentRequest;
    private ArticleContentRequest articleContentRequest;
}
