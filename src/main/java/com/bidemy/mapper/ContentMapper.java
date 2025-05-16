package com.bidemy.mapper;

import com.bidemy.model.entity.ArticleContent;
import com.bidemy.model.entity.Content;
import com.bidemy.model.entity.VideoContent;
import com.bidemy.model.request.ArticleContentRequest;
import com.bidemy.model.request.VideoContentRequest;
import com.bidemy.model.response.ArticleContentResponse;
import com.bidemy.model.response.ContentResponse;
import com.bidemy.model.response.VideoContentResponse;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    VideoContentResponse toVideoResponse(VideoContent content);

    @Mapping(target = "lessonId", source = "lesson.id")
    ArticleContentResponse toArticleResponse(ArticleContent content);

    default ContentResponse toResponse(Content content) {
        if (content instanceof VideoContent) {
            return toVideoResponse((VideoContent) content);
        } else if (content instanceof ArticleContent) {
            return toArticleResponse((ArticleContent) content);
        } else {
            throw new IllegalArgumentException("Bilinmeyen içerik türü: " + content.getClass().getSimpleName());
        }
    }

    VideoContent toVideoEntity(@Valid VideoContentRequest request);

    ArticleContent toArticleEntity(@Valid ArticleContentRequest request);

}
