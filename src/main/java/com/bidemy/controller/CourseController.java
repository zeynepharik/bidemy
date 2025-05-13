package com.bidemy.controller;

import com.bidemy.model.request.CourseRequest;
import com.bidemy.model.response.CourseResponse;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final ICourseService courseService;
    private final ICategoryService categoryService;

    @PostMapping("/new")
    public String createCourse(@ModelAttribute("courseRequest") @Valid CourseRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach((error) -> System.out.println("Validation Error: " + error.toString()));
            model.addAttribute("categories", categoryService.getAll());
            return "basics";
        } else {
            CourseResponse createdCourse = courseService.create(request);
            return "redirect:/instructor/courses/manage/curriculum?courseId=" + createdCourse.getId();
        }
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
