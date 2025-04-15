package com.bidemy.controller;

import com.bidemy.model.dto.CourseDTO;

import java.util.List;

public interface ICourseController {
    CourseDTO create(CourseDTO dto);
    CourseDTO getById(Long id);
    List<CourseDTO> getAll();
    CourseDTO update(Long id , CourseDTO dto);
    void delete(Long id);
}
