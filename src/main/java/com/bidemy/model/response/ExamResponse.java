package com.bidemy.model.response;

import com.bidemy.model.dto.QuestionDTO;

import java.util.List;

public class ExamResponse {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDTO> questionsList;
}
