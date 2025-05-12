package com.bidemy.model.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
        private Long id;
        private String title;
        private String description;
        private String pictureUrl;
        private Long categoryId;
        private Long instructorId;
        private  List<SectionDTO> sections;
        private Double price;
}
