package com.bidemy.controller;

import com.bidemy.model.request.ArticleContentRequest;
import com.bidemy.model.request.UpdateVideoContentRequest;
import com.bidemy.model.request.VideoContentRequest;
import com.bidemy.model.response.ArticleContentResponse;
import com.bidemy.model.response.ContentResponse;
import com.bidemy.model.response.VideoContentResponse;
import com.bidemy.service.IContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController{
    private final IContentService contentService;

    @PostMapping("/video")
    public VideoContentResponse createVideoContent(@RequestBody @Valid VideoContentRequest request) {
        return contentService.createVideoContent(request);
    }

    @PostMapping("/article")
    public ArticleContentResponse createArticleContent(@RequestBody @Valid ArticleContentRequest request) {
        return contentService.createArticleContent(request);
    }

    @GetMapping("/{lessonId}")
    public List<ContentResponse> getAllContentByLessonId(@PathVariable Long lessonId) {
        return contentService.getAllContentByLessonId(lessonId);
    }

    @PutMapping("/video")
    public VideoContentResponse updateVideoContent(@RequestBody @Valid UpdateVideoContentRequest request) throws IOException {
        return contentService.updateVideoContent(request);
    }

    @PutMapping("/article")
    public ArticleContentResponse updateArticleContent(@RequestBody @Valid ArticleContentRequest request) {
        return contentService.updateArticleContent(request);
    }
}
