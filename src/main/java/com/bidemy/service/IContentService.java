package com.bidemy.service;

import com.bidemy.model.request.ArticleContentRequest;
import com.bidemy.model.request.UpdateVideoContentRequest;
import com.bidemy.model.request.VideoContentRequest;
import com.bidemy.model.response.ArticleContentResponse;
import com.bidemy.model.response.ContentResponse;
import com.bidemy.model.response.VideoContentResponse;

import java.io.IOException;
import java.util.List;

public interface IContentService {
    VideoContentResponse createVideoContent(VideoContentRequest request);

    ArticleContentResponse createArticleContent(ArticleContentRequest request);

    List<ContentResponse> getAllContentByLessonId(Long lessonId);

    VideoContentResponse updateVideoContent(UpdateVideoContentRequest request) throws IOException;

    ArticleContentResponse updateArticleContent(ArticleContentRequest request);
}
