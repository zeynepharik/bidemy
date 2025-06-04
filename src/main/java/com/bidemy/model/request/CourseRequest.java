package com.bidemy.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
public class CourseRequest {

    private Long id;

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Course description is required")
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Course price is required")
    @Min(value = 250, message = "Minimum price is 250")
    @Max(value = 3000, message = "Maximum price is 3000")
    private Double price;

    @Size(max = 1048576, message = "Image size is too large") // opsiyonel
    private String base64Image;

    @Valid
    private List<SectionRequest> sections=new ArrayList<>();
}
