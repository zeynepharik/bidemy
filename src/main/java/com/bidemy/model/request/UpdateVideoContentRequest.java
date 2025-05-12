package com.bidemy.model.request;

import com.bidemy.model.dto.VideoContentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoContentRequest {
    @Valid
    private VideoContentDTO dto;

    @NotBlank(message = "New video content (base64) cannot be blank")
    private String newBase64Video;
}
