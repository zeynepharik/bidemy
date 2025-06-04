package com.bidemy.controller;

import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.SectionResponse;
import com.bidemy.service.ICourseService;
import com.bidemy.service.ISectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {
    private final ISectionService sectionService;
    private final ICourseService courseService;

    @PostMapping("/save")
    public String create(@ModelAttribute("course") SectionRequest request, Model model) {
        sectionService.createSection(request);
        courseService.publishCourse(request.getCourseId());
        return "redirect:/instructor/courses";
    }

    @PostMapping(value = "/api", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public SectionResponse createJson(@RequestBody @Valid SectionRequest request) {
        SectionResponse created = sectionService.createSection(request);
        courseService.publishCourse(request.getCourseId());
        return created;
    }

    @GetMapping("/{id}")
    public SectionResponse getById(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<SectionResponse> getAllByCourseId(@PathVariable Long courseId) {
        return sectionService.getAllSectionByCourseId(courseId);
    }

    @PutMapping("/{id}")
    public SectionResponse update(@PathVariable Long id,  @Valid SectionRequest request) {
        return sectionService.updateSection(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }
}
