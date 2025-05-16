package com.bidemy.controller;

import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.request.*;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ISectionService;
import com.bidemy.service.IUserService;
import com.bidemy.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ISectionService iSectionService;
    private final ICategoryService iCategoryService;
    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/instructor/courses")
    public String showInstructorCoursesPage(Model model) {
        return "instructor-courses";
    }

    @GetMapping("instructor/courses/manage/course-structure")
    public String showCourseStructureForm(Model model) {
        return "course-structure";
    }

    @GetMapping("/instructor/courses/manage/setup")
    public String showSetupPage(Model model) {
        model.addAttribute("active", "setup");
        return "setup";
    }

    @GetMapping("/instructor/courses/manage/film")
    public String showFilmPage(Model model) {
        model.addAttribute("active", "film");
        return "film";
    }

    @GetMapping("/instructor/courses/manage/curriculum")
    public String showCurriculumPage(@RequestParam("courseId") Long courseId, Model model) {
//        model.addAttribute("courseId", courseId);
        model.addAttribute("sections", new SectionRequest());
//        model.addAttribute("lesson", new LessonRequest());
//        model.addAttribute("article", new ArticleContentRequest());
//        model.addAttribute("video", new VideoContentRequest());
//        model.addAttribute("exam", new ExamRequest());
        model.addAttribute("active", "curriculum");
        return "curriculum";
    }

    @GetMapping("/instructor/courses/manage/basics")
    public String showBasicsPage(Model model) {
        model.addAttribute("courseRequest", new CourseRequest());
        model.addAttribute("active", "basics");
        model.addAttribute("categories", categoryServiceImpl.getAll());
        return "basics";
    }

    @GetMapping("/instructor/courses/manage/pricing")
    public String showPricingPage(Model model) {
        model.addAttribute("active", "pricing");
        return "pricing";
    }

    @PostMapping
    public UserDTO create(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
