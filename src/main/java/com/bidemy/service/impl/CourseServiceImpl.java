package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.CourseMapper;
import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.entity.Category;
import com.bidemy.model.entity.Course;
import com.bidemy.model.entity.User;
import com.bidemy.repository.CategoryRepository;
import com.bidemy.repository.CourseRepository;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public Course createCourse(CourseDTO courseDTO, MultipartFile file, Principal principal) throws IOException {
        if (file.isEmpty()) {
            throw new BusinessValidationException(BusinessValidationRule.FILE_IS_EMPTY);
        }


        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        if (!Arrays.asList("mp4", "mov", "avi", "mkv").contains(fileExtension)) {
            throw new BusinessValidationException(BusinessValidationRule.INVALID_FILE_TYPE);
        }

        if (file.getSize() > 100 * 1024 * 1024) { // 100 MB
            throw new BusinessValidationException(BusinessValidationRule.FILE_TOO_LARGE);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path videoPath = Paths.get(uploadDir + File.separator + "videos" + File.separator + fileName);

        Files.createDirectories(videoPath.getParent());
        Files.write(videoPath, file.getBytes());

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setUrl(courseDTO.getUrl());
        course.setPrice(courseDTO.getPrice());
        course.setPictureUrl("/videos/" + fileName);

        Category category = categoryRepository.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));
        course.setCategory(category);

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.USER_NOT_FOUND));
        course.setInstructor(user);

        return courseRepository.save(course);

    }

    @Override
    public CourseDTO create(CourseDTO dto) {
        Course course = courseMapper.toEntity(dto);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDTO getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDTO> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(courseMapper.toDto(course));
        }
        return courseDTOS;
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        course.setTitle(dto.getTitle());
        course.setUrl(dto.getUrl());
        course.setDescription(dto.getDescription());
        course.setCategory(new Category(dto.getCategoryId(), null));
        course.setInstructor(new User(dto.getInstructorId(), null, null, null));
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public void delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }
}
