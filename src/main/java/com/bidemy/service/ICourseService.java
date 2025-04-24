package com.bidemy.service;


import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.entity.Course;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ICourseService {
    CourseDTO create(CourseDTO dto);

    Course createCourse(CourseDTO courseDTO, MultipartFile file, Principal principal) throws IOException;

    CourseDTO getById(Long id);

    List<CourseDTO> getAll();

    CourseDTO update(Long id, CourseDTO dto);

    void delete(Long id);
}
