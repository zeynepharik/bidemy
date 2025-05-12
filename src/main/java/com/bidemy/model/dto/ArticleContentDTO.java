package com.bidemy.model.dto;

import jakarta.persistence.Lob;

public class ArticleContentDTO extends ContentDTO {
    @Lob
    private  String text;

}
