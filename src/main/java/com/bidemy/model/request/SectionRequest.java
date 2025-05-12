package com.bidemy.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    private Long courseId;

    @Valid
    private List<LessonRequest> lessonList;
}
