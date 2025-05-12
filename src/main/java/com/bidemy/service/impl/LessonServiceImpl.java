package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.model.entity.Exam;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.Section;
import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.response.LessonResponse;
import com.bidemy.repository.LessonRepository;
import com.bidemy.repository.SectionRepository;
import com.bidemy.service.ILessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;
    private final SectionRepository sectionRepository;
    private final ContentMapper contentMapper;
    private final ExamMapper examMapper;

    public LessonResponse getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
        return lessonMapper.toResponse(lesson);
    }

    public LessonResponse createLesson(Long sectionId, LessonRequest lessonRequest) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.SECTION_NOT_FOUND));
        Lesson lesson = lessonMapper.toEntity(lessonRequest);
        lesson.setSection(section);

        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toResponse(savedLesson);
    }

    public List<LessonResponse> getAllLessonsBySectionId(Long sectionId) {
        return lessonRepository.findAllBySectionId(sectionId)
                .stream()
                .map(lessonMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LessonResponse updateLesson(Long lessonId, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setContent(contentMapper.toEntity(lessonRequest.getContent()));

        if (lessonRequest.getExams() != null) {
            List<Exam> exams = examMapper.toEntities(lessonRequest.getExams());

            for(Exam exam : exams) {
                exam.setLesson(lesson);
            }

            lesson.getExams().clear();
            lesson.getExams().addAll(exams);
        }

        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toResponse(updatedLesson);
    }

    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
        lessonRepository.delete(lesson);
    }
}
