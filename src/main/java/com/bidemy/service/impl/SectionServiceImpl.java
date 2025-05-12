package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.model.entity.Course;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.Section;
import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.SectionResponse;
import com.bidemy.repository.CourseRepository;
import com.bidemy.repository.SectionRepository;
import com.bidemy.service.ISectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionServiceImpl implements ISectionService {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    public SectionResponse createSection(SectionRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        Section section = sectionMapper.toEntity(request);
        section.setCourse(course);

        List<Lesson> lessons = lessonMapper.toLessonEntities(request.getLessonList());
        section.setLessonList(lessons);
        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    public SectionResponse getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.SECTION_NOT_FOUND));
        return sectionMapper.toResponse(section);
    }

    public List<SectionResponse> getAllSectionByCourseId(Long courseId) {
        List<Section> sections = sectionRepository.findAllByCourseId(courseId);
        return sections.stream()
                .map(sectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SectionResponse updateSection(Long id, SectionRequest request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.SECTION_NOT_FOUND));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));

        section.setTitle(request.getTitle());
        section.setCourse(course);
        if (request.getLessonList() != null && !request.getLessonList().isEmpty()) {
            List<Lesson> lessons = lessonMapper.toLessonEntities(request.getLessonList());

            for(Lesson lesson : lessons) {
                lesson.setSection(section);
            }

            section.getLessonList().clear();
            section.getLessonList().addAll(lessons);
        }

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    public void deleteSection(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.SECTION_NOT_FOUND));
        Course course = section.getCourse();
        if (course != null) {
            course.getSections().removeIf((sec) -> sec.getId().equals(id));
            courseRepository.save(course);
        }

        sectionRepository.delete(section);
    }
}
