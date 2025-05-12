package com.bidemy.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequest {
    @NotBlank(message = "Option text is required")
    private String text;

    @NotNull(message = "Option correctness must be specified")
    private Boolean isCorrect;
}
