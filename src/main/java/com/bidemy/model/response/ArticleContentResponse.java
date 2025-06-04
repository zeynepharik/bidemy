package com.bidemy.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleContentResponse {
    private Long id;
    private String description;
    private Long lessonId;
    private String text;
}
