package com.bidemy.controller.impl;

import com.bidemy.controller.ICourseController;
import com.bidemy.model.dto.CourseDTO;
import com.bidemy.repository.CategoryRepository;
import com.bidemy.repository.CourseRepository;
import com.bidemy.repository.UserRepository;
import com.bidemy.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseControllerImpl implements ICourseController {

    private final ICourseService courseService;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    @Override
    public CourseDTO create(@RequestBody CourseDTO dto) {
        return courseService.create(dto);
    }

    @GetMapping("instructor/courses")
    public String courses(){
        return "instructor-courses";
    }

    @GetMapping("/instructor/courses/manage/course-structure")
    public String coursesManage(Model model){
        model.addAttribute("courses",courseService.getAll());
        model.addAttribute("course",new CourseDTO());
        return "course-structure";
    }

    @GetMapping("/instructor/courses/manage/curriculum")
    public String curriculum(){
        return "curriculum";
    }

    @GetMapping("/instructor/courses/manage/setup")
    public String setup(){
        return "setup";
    }

    @GetMapping("/instructor/courses/manage/film")
    public String film(){
        return "film";
    }
    
    @GetMapping("/instructor/courses/manage/basics")
    public String basics(Model model){
        model.addAttribute("courseDTO",new CourseDTO());
        return "basics";
    }

    @GetMapping("/instructor/courses/manage/pricing")
    public String pricing(Model model){
        model.addAttribute("courseDTO",new CourseDTO());
        return "pricing";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute CourseDTO courseDTO,
                               @RequestParam("file") MultipartFile file,
                               Principal principal) throws IOException {

        courseService.createCourse(courseDTO, file, principal);

        return "redirect:/instructor/courses";
    }


    @GetMapping("/{id}")
    @Override
    public CourseDTO getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @GetMapping
    @Override
    public List<CourseDTO> getAll() {
        return courseService.getAll();
    }

    @PutMapping("/{id}")
    @Override
    public CourseDTO update(@PathVariable Long id, @RequestBody CourseDTO dto) {
        return courseService.update(id,dto);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
