package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.ContentMapper;
import com.bidemy.model.entity.ArticleContent;
import com.bidemy.model.entity.Content;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.request.ArticleContentRequest;
import com.bidemy.model.response.ArticleContentResponse;
import com.bidemy.repository.ArticleContentRepository;
import com.bidemy.repository.ContentRepository;
import com.bidemy.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleContentServiceImpl {

    private final LessonRepository lessonRepository;
    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;
    private final ArticleContentRepository articleContentRepository;

    private Lesson getLessonOrThrow(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
    }

    public ArticleContentResponse createArticleContent(ArticleContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setDescription(request.getDescription());
        articleContent.setText(request.getText());
        articleContent.setLesson(lesson);
        return contentMapper.toArticleResponse(articleContentRepository.save(articleContent));
    }

    public ArticleContentResponse updateArticleContent(ArticleContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());
        ArticleContent articleContent = lesson.getArticleContent();

        articleContent.setDescription(request.getDescription());
        articleContent.setText(request.getText());
        articleContent.setLesson(lesson);
        return contentMapper.toArticleResponse(articleContentRepository.save(articleContent));
    }
}