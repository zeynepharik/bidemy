package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.ContentMapper;
import com.bidemy.model.entity.ArticleContent;
import com.bidemy.model.entity.Content;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.VideoContent;
import com.bidemy.model.request.ArticleContentRequest;
import com.bidemy.model.request.UpdateVideoContentRequest;
import com.bidemy.model.request.VideoContentRequest;
import com.bidemy.model.response.ArticleContentResponse;
import com.bidemy.model.response.ContentResponse;
import com.bidemy.model.response.VideoContentResponse;
import com.bidemy.repository.ContentRepository;
import com.bidemy.repository.LessonRepository;
import com.bidemy.service.IContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentServiceImpl implements IContentService {
    private final ContentRepository contentRepository;
    private final LessonRepository lessonRepository;
    private final ContentMapper contentMapper;

    @Value("${video.storage.path}")
    private String videoStoregePath;

    public VideoContentResponse createVideoContent(VideoContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());

        String videoUrl;
        try {
            videoUrl = saveVideoFile(request.getBase64Video());
        } catch (IOException e) {
            throw new RuntimeException("Video yüklenirken hata oluştu: " + e.getMessage());
        }

        VideoContent videoContent = new VideoContent();
        videoContent.setVideoUrl(videoUrl);
        videoContent.setDescription(request.getDescription());
        videoContent.setLesson(lesson);
        VideoContent savedContent = contentRepository.save(videoContent);
        return contentMapper.toVideoResponse(savedContent);
    }

    public ArticleContentResponse createArticleContent(ArticleContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setDescription(request.getDescription());
        articleContent.setText(request.getText());
        articleContent.setLesson(lesson);
        return contentMapper.toArticleResponse(contentRepository.save(articleContent));
    }

    public List<ContentResponse> getAllContentByLessonId(Long lessonId) {
        return contentRepository.findAllByLesson_Id(lessonId).stream().map(contentMapper::toResponse).collect(Collectors.toList());

    }

    public VideoContentResponse updateVideoContent(UpdateVideoContentRequest request) throws IOException {
        Lesson lesson = getLessonOrThrow(request.getDto().getLessonId());
        Content content = lesson.getContent();
        if (content != null && content instanceof VideoContent videoContent) {
            videoContent.setDescription(request.getDto().getDescription());
            if (request.getNewBase64Video() != null && !request.getNewBase64Video().isEmpty()) {
                String newVideoUrl = saveVideoFile(request.getNewBase64Video(), videoContent.getVideoUrl());
                videoContent.setVideoUrl(newVideoUrl);
            }

            return contentMapper.toVideoResponse(contentRepository.save(videoContent));
        } else {
            throw new BusinessValidationException(BusinessValidationRule.CONTENT_NOT_FOUND);
        }
    }

    public ArticleContentResponse updateArticleContent(ArticleContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());
        Content content = lesson.getContent();
        if (content != null && content instanceof ArticleContent articleContent) {
            articleContent.setDescription(request.getDescription());
            articleContent.setText(request.getText());
            return contentMapper.toArticleResponse(contentRepository.save(articleContent));
        } else {
            throw new BusinessValidationException(BusinessValidationRule.CONTENT_NOT_FOUND);
        }
    }

    private String saveVideoFile(String base64Data, String oldVideoUrl) throws IOException {
        base64Data = base64Data.replaceAll("\\s", "");
        File dir = new File(videoStoregePath);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Video dizini oluşturulamadı.");
        } else {
            if (oldVideoUrl != null && !oldVideoUrl.isEmpty()) {
                String oldFileName = oldVideoUrl.substring(oldVideoUrl.lastIndexOf("/") + 1);
                Path oldFilePath = Paths.get(videoStoregePath, oldFileName);
                Files.deleteIfExists(oldFilePath);
            }

            byte[] videoBytes = Base64.getDecoder().decode(base64Data);
            String fileName = UUID.randomUUID().toString() + ".mp4";
            Path path = Paths.get(videoStoregePath, fileName);
            Files.write(path, videoBytes);
            return "http://localhost:8082/videos/" + fileName;
        }
    }

    private String saveVideoFile(String base64Data) throws IOException {
        return saveVideoFile(base64Data, null);
    }

    private Lesson getLessonOrThrow(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
    }
}