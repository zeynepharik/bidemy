package com.bidemy.controller;

import com.bidemy.mapper.CourseMapper;
import com.bidemy.model.dto.OptionDTO;
import com.bidemy.model.dto.QuestionDTO;
import com.bidemy.model.dto.UserDTO;
import com.bidemy.model.request.*;
import com.bidemy.model.response.CourseResponse;
import com.bidemy.service.ICategoryService;
import com.bidemy.service.ICourseService;
import com.bidemy.service.ISectionService;
import com.bidemy.service.IUserService;
import com.bidemy.service.impl.CategoryServiceImpl;
import com.bidemy.service.impl.SectionServiceImpl;
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
    private final SectionServiceImpl sectionServiceImpl;
    private final ICourseService courseService;
    private final CourseMapper courseMapper;

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

        CourseResponse courseResponse = courseService.getById(courseId);
        CourseRequest courseRequest = courseMapper.toCourseRequest(courseResponse);

        for (SectionRequest section : courseRequest.getSections()) {
            if (section.getLessonList() != null) {
                for (LessonRequest lesson : section.getLessonList()) {
                    if (lesson.getExams() == null || lesson.getExams().isEmpty()) {
                        ExamRequest emptyExam = new ExamRequest();
                        emptyExam.setTitle("");
                        emptyExam.setDescription("");
                        emptyExam.setPlaceholder(true);

                        QuestionDTO emptyQuestion = new QuestionDTO();
                        emptyQuestion.setText("");
                        emptyQuestion.setPlaceholder(true);

                        OptionDTO emptyOption = new OptionDTO();
                        emptyOption.setText("");
                        emptyOption.setIsCorrect(false);
                        emptyOption.setPlaceholder(true);

                        emptyQuestion.setOptions(List.of(emptyOption));
                        emptyExam.setQuestionsList(List.of(emptyQuestion));

                        lesson.setExams(List.of(emptyExam));
                    }
                }
            }
        }
        model.addAttribute("course", courseRequest);
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
