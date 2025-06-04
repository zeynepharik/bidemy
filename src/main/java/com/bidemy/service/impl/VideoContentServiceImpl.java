package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.ContentMapper;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.VideoContent;
import com.bidemy.model.request.UpdateVideoContentRequest;
import com.bidemy.model.request.VideoContentRequest;
import com.bidemy.model.response.VideoContentResponse;
import com.bidemy.repository.LessonRepository;
import com.bidemy.repository.VideoContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoContentServiceImpl {


    private final LessonRepository lessonRepository;
    private final ContentMapper contentMapper;
    private final VideoContentRepository videoContentRepository;

    private Lesson getLessonOrThrow(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
    }

    public VideoContentResponse createVideoContent(VideoContentRequest request) {
        Lesson lesson = getLessonOrThrow(request.getLessonId());

        VideoContent videoContent = new VideoContent();
        videoContent.setBase64Video(request.getBase64Video());
        videoContent.setDescription(request.getDescription());
        videoContent.setLesson(lesson);
        VideoContent savedContent = videoContentRepository.save(videoContent);
        return contentMapper.toVideoResponse(savedContent);
    }

    public VideoContentResponse updateVideoContent(UpdateVideoContentRequest request) throws IOException {
        Lesson lesson = getLessonOrThrow(request.getDto().getLessonId());
        VideoContent videoContent = lesson.getVideoContent();


        videoContent.setDescription(request.getDto().getDescription());
        videoContent.setBase64Video(request.getNewBase64Video());
        return contentMapper.toVideoResponse(videoContentRepository.save(videoContent));

    }
}
