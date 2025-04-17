package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.CourseMapper;
import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.entity.Category;
import com.bidemy.model.entity.Course;
import com.bidemy.model.entity.User;
import com.bidemy.repository.CourseRepository;
import com.bidemy.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    @Override
    public CourseDTO create(CourseDTO dto) {
        Course course = courseMapper.toEntity(dto);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDTO getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDTO> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses){
            courseDTOS.add(courseMapper.toDto(course));
        }
        return courseDTOS;
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        course.setTitle(dto.getTitle());
        course.setUrl(dto.getUrl());
        course.setDescription(dto.getDescription());
        course.setCategory(new Category(dto.getCategoryId(),null));
        course.setInstructor(new User(dto.getInstructorId(),null,null,null));
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public void delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }
}
