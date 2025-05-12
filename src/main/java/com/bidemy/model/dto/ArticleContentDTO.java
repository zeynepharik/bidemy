package com.bidemy.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

public class ArticleContentDTO extends ContentDTO {
    @Lob
    @NotBlank(message = "Text cannot be blank")
    private  String text;

}
