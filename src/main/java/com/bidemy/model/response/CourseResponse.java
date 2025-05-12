package com.bidemy.model.response;

import java.util.List;

public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String pictureUrl;
    private Long categoryId;
    private Long instructorId;
    private Double price;
    private List<SectionResponse> sections;

}
