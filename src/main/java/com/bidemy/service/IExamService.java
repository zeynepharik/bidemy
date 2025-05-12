package com.bidemy.service;

import com.bidemy.model.request.ExamRequest;
import com.bidemy.model.response.ExamResponse;

import java.util.List;

public interface IExamService {
    ExamResponse createExam(ExamRequest request);

    ExamResponse updateExam(Long id, ExamRequest request);

    void deleteExam(Long id);

    ExamResponse getExamById(Long id);

    List<ExamResponse> getExamsByLessonId(Long lessonId);
}
