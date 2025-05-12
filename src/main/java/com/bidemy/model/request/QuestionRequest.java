package com.bidemy.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class QuestionRequest {
    @NotBlank(message = "Question text is required")
    private String text;

    @Valid
    @NotNull(message = "Options list cannot be null")
    private List<OptionRequest> options;
}
