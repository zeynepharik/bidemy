package com.bidemy.model.dto;

import java.util.List;

public class LessonDTO {
    private Long id;
    private String title;
    private Long sectionId;
    private List<ExamDTO> examDTOS;
    private ContentDTO content;
}
