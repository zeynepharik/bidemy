package com.bidemy.dto;

import jakarta.validation.constraints.Min;
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
public class VideoDTO {
    @NotNull(message = "Video ID cannot be null")
    private Long id;

    @NotEmpty(message = "Video title is required")
    private String title;

    @NotEmpty(message = "Video URL is required")
    private String url;

    @Min(value = 1,message = "Video duration must be at least 1 second")
    private int duration;

    @NotNull(message = "Course ID is required")
    private Long courseId;

}
