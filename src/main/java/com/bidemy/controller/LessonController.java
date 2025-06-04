package com.bidemy.controller;

import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.response.LessonResponse;
import com.bidemy.service.ILessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final ILessonService lessonService;
    private final UserController userController;

    @GetMapping("/create/{sectionId}")
    public String showCreateLessonForm(@PathVariable Long sectionId, Model model) {
        model.addAttribute("someSectionId", sectionId);
        model.addAttribute("lesson", new LessonRequest());
        model.addAttribute("lessons", lessonService.getAllLessonsBySectionId(sectionId));
        return "curriculum";
    }

    @PostMapping("/{sectionId}")
    public String createLesson(@PathVariable Long sectionId, @ModelAttribute("lesson") @Valid LessonRequest lessonRequest, @RequestParam("courseId") Long courseId, Model model) {
        lessonService.createLesson(sectionId, lessonRequest);
        return "redirect:/instructor/courses/manage/curriculum?courseId=" + courseId;    }

    @PostMapping(value = "/{sectionId}/lessons/api", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LessonResponse createLessonApi(@PathVariable Long sectionId, @RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.createLesson(sectionId, lessonRequest);
    }

    @GetMapping("/{id}")
    public LessonResponse getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/section/{sectionId}")
    public List<LessonResponse> getAllLessonsBySectionId(@PathVariable Long sectionId) {
        return lessonService.getAllLessonsBySectionId(sectionId);
    }

    @PutMapping("/{lessonId}")
    public LessonResponse updateLesson(@PathVariable Long lessonId, @RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.updateLesson(lessonId, lessonRequest);
    }
}
