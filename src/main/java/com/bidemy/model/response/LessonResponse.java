package com.bidemy.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String title;
    private List<ExamResponse> exams;
    private ArticleContentResponse articleContentResponse;
    private VideoContentResponse videoContentResponse;
}
