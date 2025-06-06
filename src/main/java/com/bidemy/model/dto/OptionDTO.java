package com.bidemy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO {
    private Long id;
    private String text;
    private Boolean isCorrect;
    private Boolean placeholder = false;

}
