package com.bidemy.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("VIDEO")
public class VideoContent extends Content {
    private String videoUrl;
}
