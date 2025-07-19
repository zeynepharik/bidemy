package com.bidemy.service;

import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.response.LessonResponse;

import java.util.List;

public interface ILessonService {
    LessonResponse createLesson(LessonRequest lessonRequest);

    LessonResponse getLessonById(Long id);

    List<LessonResponse> getAllLessonsBySectionId(Long sectionId);

    LessonResponse updateLesson(Long sectionId, LessonRequest lessonRequest);

    void deleteLesson(Long lessonId);
}
