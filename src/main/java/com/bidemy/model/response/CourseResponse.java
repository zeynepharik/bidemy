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
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String base64Image;
    private Long categoryId;
    private Long instructorId;
    private Double price;
    private List<SectionResponse> sections;

}
