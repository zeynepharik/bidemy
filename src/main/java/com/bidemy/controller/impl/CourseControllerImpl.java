package com.bidemy.controller.impl;

import com.bidemy.controller.ICourseController;
import com.bidemy.model.dto.CourseDTO;
import com.bidemy.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseControllerImpl implements ICourseController {

    private final ICourseService courseService;

    @PostMapping
    @Override
    public CourseDTO create(@RequestBody CourseDTO dto) {
        return courseService.create(dto);
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
