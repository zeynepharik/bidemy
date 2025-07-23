package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.CourseMapper;
import com.bidemy.mapper.SectionMapper;
import com.bidemy.model.entity.Category;
import com.bidemy.model.entity.Course;
import com.bidemy.model.entity.CourseStatus;
import com.bidemy.model.entity.Section;
import com.bidemy.model.request.CourseRequest;
import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.CourseResponse;
import com.bidemy.repository.CategoryRepository;
import com.bidemy.repository.CourseRepository;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.ICourseService;
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

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {
    @Value("${image.storage.path}")
    private String imageStoragePath;

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SectionMapper sectionMapper;

    public CourseResponse create(CourseRequest request) {
        System.out.println("Kurs oluşturuluyor: " + request);
        String pictureUrl = null;
//        if (request.getBase64Image() != null && !request.getBase64Image().isEmpty()) {
//            try {
//                pictureUrl = saveImageFile(request.getBase64Image());
//            } catch (IOException e) {
//                throw new BusinessValidationException(BusinessValidationRule.IMAGE_UPLOAD_FAILED);
//            }
//        }

        Course course = new Course();
        Section section = new Section();
        section.setTitle("Giriş");

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setBase64Image(request.getBase64Image());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        section.setCourse(course);
        course.setStatus(CourseStatus.DRAFT);
        course.setSections(List.of(section));
        course = courseRepository.save(course);


        return courseMapper.toResponse(course);
    }

    public CourseResponse update(Long id, CourseRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Course id must not be null");
        }
        Course course = courseRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        if (request.getSections() != null) {
            for (SectionRequest section : request.getSections()) {
                if (section.getLessonList() != null) {
                    for (LessonRequest lesson : section.getLessonList()) {
                        if (lesson.getExams() != null) {
                            lesson.setExams(
                                    lesson.getExams().stream()
                                            .filter(exam -> !Boolean.TRUE.equals(exam.getPlaceholder()) &&
                                                    org.springframework.util.StringUtils.hasText(exam.getTitle()))
                                            .peek(exam -> {
                                                if (exam.getQuestionsList() != null) {
                                                    exam.setQuestionsList(
                                                            exam.getQuestionsList().stream()
                                                                    .filter(q -> !Boolean.TRUE.equals(q.getPlaceholder()) &&
                                                                            org.springframework.util.StringUtils.hasText(q.getText()))
                                                                    .peek(q -> {
                                                                        if (q.getOptions() != null) {
                                                                            q.setOptions(
                                                                                    q.getOptions().stream()
                                                                                            .filter(opt -> !Boolean.TRUE.equals(opt.getPlaceholder()) &&
                                                                                                    org.springframework.util.StringUtils.hasText(opt.getText()))
                                                                                            .toList()
                                                                            );
                                                                        }
                                                                    })
                                                                    .toList()
                                                    );
                                                }
                                            })
                                            .toList()
                            );
                        }
                    }
                }
            }
        }
        course.setTitle(request.getTitle());
        course.setBase64Image(request.getBase64Image());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        Category category = categoryRepository.findById(course.getCategory().getId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        course.setSections(request.getSections().stream().map(s->sectionMapper.toEntity(s)).toList());
        course = courseRepository.save(course);


        return courseMapper.toResponse(course);
    }

    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        return courseMapper.toResponse(course);
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }

    private String saveImageFile(String base64Image) throws IOException {
        return saveImageFile(base64Image, null);
    }

    private String saveImageFile(String base64Image, String oldImageUrl) throws IOException {
        base64Image = base64Image.replaceAll("\\s", "");
        File dir = new File(imageStoragePath);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Resim dizini oluşturulamadı.");
        } else {
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
                Path oldFilePath = Paths.get(imageStoragePath, oldFileName);
                Files.deleteIfExists(oldFilePath);
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            String fileName = UUID.randomUUID().toString() + ".png";
            Path path = Paths.get(imageStoragePath, fileName);
            Files.write(path, imageBytes);
            return "http://localhost:8082/course-images/" + fileName;
        }
    }

    public void publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        course.setStatus(CourseStatus.PUBLISHED);
        courseRepository.save(course);
    }
}
