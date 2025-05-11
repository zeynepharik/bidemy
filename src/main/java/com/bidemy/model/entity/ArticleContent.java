package com.bidemy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("ARTICLE")
public class ArticleContent extends Content{
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
}
