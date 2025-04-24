package com.bidemy.controller;

import com.bidemy.model.dto.CourseDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ICourseController {
    CourseDTO create(CourseDTO dto);
    public String createCourse(@ModelAttribute CourseDTO courseDTO,
                               @RequestParam("file") MultipartFile file,
                               Principal principal) throws IOException;
    public String courses();
    public String coursesManage(Model model);
    public String curriculum();
    public String setup();
    CourseDTO getById(Long id);
    List<CourseDTO> getAll();
    CourseDTO update(Long id , CourseDTO dto);
    void delete(Long id);
}
