package com.bidemy.model.dto;

import java.util.List;

public class ExamDTO {
    private Long id;
    private String title;
    private Long lessonId;
    private String description;
    private List<QuestionDTO> questionsList;
}
