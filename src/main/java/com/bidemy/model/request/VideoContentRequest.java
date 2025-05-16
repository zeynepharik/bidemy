package com.bidemy.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoContentRequest {
    @NotBlank(message = "Description is required")
    private String description;
    private Long lessonId;
    @NotBlank(message = "New video content (base64) cannot be blank")
    private String base64Video;
}
