package com.bidemy.controller;

import com.bidemy.model.request.CourseRequest;
import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.CourseResponse;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final ICourseService courseService;
    private final ICategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @PostMapping("/new")
    public String createCourse(@ModelAttribute("course") @Valid CourseRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors())
                if (error instanceof FieldError fieldError) {
                    logger.warn("Validation Error - Field {},Message: {}",
                            fieldError.getField(),
                            fieldError.getDefaultMessage());
                } else {
                    logger.warn("Validation Error: {}", error.getDefaultMessage());
                }
            model.addAttribute("categories", categoryService.getAll());
            return "basics";
        } else {
            CourseResponse createdCourse = courseService.create(request);
            return "redirect:/instructor/courses/manage/curriculum?courseId=" + createdCourse.getId();
        }
    }

    @PostMapping("/manage/curriculum/save")
    public String saveCurriculum(@ModelAttribute("course") CourseRequest courseRequest) throws IOException {

        if (courseRequest.getId() == null) {
            throw new IllegalArgumentException("Course ID must not be null");
        }

        courseService.update(courseRequest.getId(), courseRequest);

        return "redirect:/instructor/courses/manage/curriculum?courseId=" + courseRequest.getId();
    }


    @PutMapping("/{id}")
    public CourseResponse updateCourse(@PathVariable Long id, @RequestBody @Valid CourseRequest request) throws IOException {
        return courseService.update(id, request);
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @GetMapping
    public List<CourseResponse> getAll() {
        return courseService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
