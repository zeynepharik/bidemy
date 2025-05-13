package com.bidemy.controller;

import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.SectionResponse;
import com.bidemy.service.ICourseService;
import com.bidemy.service.ISectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {
    private final ISectionService sectionService;
    private final ICourseService courseService;

    @PostMapping("/save")
    public String create(@RequestBody @Valid SectionRequest request) {
        sectionService.createSection(request);
        courseService.publishCourse(request.getCourseId());
        return "redirect:/instructor/courses";
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
    public SectionResponse update(@PathVariable Long id, @RequestBody @Valid SectionRequest request) {
        return sectionService.updateSection(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }
}
