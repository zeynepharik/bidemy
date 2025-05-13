package com.bidemy.controller;

import com.bidemy.model.request.ExamRequest;
import com.bidemy.model.response.ExamResponse;
import com.bidemy.service.IExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {
    private final IExamService examService;

    @PostMapping
    public ExamResponse createExam(@RequestBody @Valid ExamRequest request) {
        return examService.createExam(request);
    }

    @PutMapping("/{id}")
    public ExamResponse updateExam(@PathVariable  Long id, @RequestBody @Valid ExamRequest request) {
        return examService.updateExam(id, request);
    }

    @GetMapping("/{id}")
    public ExamResponse getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @GetMapping("/lesson/{lessonId}")
    public List<ExamResponse> getExamsByLesson(@PathVariable Long lessonId) {
        return examService.getExamsByLessonId(lessonId);
    }

    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }
}
