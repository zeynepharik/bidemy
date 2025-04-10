package com.bidemy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @NotNull(message = "Course ID cannot be null")
    private Long id;

    @NotEmpty(message = "Course title is required")
    private String title;

    @NotEmpty(message = "Course description is required")
    private String description;

    @NotNull(message = "User ID is required")
    private Long userId;
}
