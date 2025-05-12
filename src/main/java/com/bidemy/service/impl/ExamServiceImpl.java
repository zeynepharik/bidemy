package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.model.dto.OptionDTO;
import com.bidemy.model.dto.QuestionDTO;
import com.bidemy.model.entity.Exam;
import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.Option;
import com.bidemy.model.entity.Question;
import com.bidemy.model.request.ExamRequest;
import com.bidemy.model.response.ExamResponse;
import com.bidemy.repository.ExamRepository;
import com.bidemy.repository.LessonRepository;
import com.bidemy.service.IExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements IExamService {

    private final ExamMapper examMapper;
    private final LessonRepository lessonRepository;
    private final ExamRepository examRepository;

    public ExamResponse createExam(ExamRequest request) {
        Exam exam = examMapper.toExam(request);
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.LESSON_NOT_FOUND));
        exam.setLesson(lesson);
        if (request.getQuestionsList() != null) {
            syncQuestions(exam, request.getQuestionsList());
        }

        return examMapper.toExamResponse(examRepository.save(exam));
    }

    public ExamResponse updateExam(Long id, ExamRequest request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.EXAM_NOT_FOUND));
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        if (request.getQuestionsList() != null) {
            syncQuestions(exam, request.getQuestionsList());
        }

        return examMapper.toExamResponse(examRepository.save(exam));
    }

    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.EXAM_NOT_FOUND));
        examRepository.delete(exam);
    }

    public ExamResponse getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.EXAM_NOT_FOUND));
        return examMapper.toExamResponse(exam);
    }

    public List<ExamResponse> getExamsByLessonId(Long lessonId) {
        return examRepository.findByLessonId(lessonId).stream().map(examMapper::toExamResponse).collect(Collectors.toList());
    }

    private void syncQuestions(Exam exam, List<QuestionDTO> questionDTOs) {
        Map<Long, Question> existingQuestions = new HashMap<>();

        if (exam.getQuestionsList() != null) {
            existingQuestions = exam.getQuestionsList().stream()
                    .filter(q -> q.getId() != null)
                    .collect(Collectors.toMap(Question::getId, Function.identity()));
        }

        List<Question> updatedQuestions = new ArrayList<>();

        for (QuestionDTO dto : questionDTOs) {
            Question question;

            if (dto.getId() != null && existingQuestions.containsKey(dto.getId())) {
                question = existingQuestions.get(dto.getId());
            } else {
                question = new Question();
            }

            question.setText(dto.getText());
            question.setExam(exam);
            syncOptions(question, dto.getOptions());

            updatedQuestions.add(question);
        }

        exam.setQuestionsList(updatedQuestions);
    }

    private void syncOptions(Question question, List<OptionDTO> optionDTOs) {
        Map<Long, Option> existingOptions = new HashMap<>();
        if (question.getId() != null) {
            existingOptions = question.getOptions().stream()
                    .filter(o -> o.getId() != null)
                    .collect(Collectors.toMap(Option::getId, Function.identity()));
        }

        List<Option> updatedOptions = new ArrayList<>();

        for (OptionDTO dto : optionDTOs) {
            Option option = (dto.getId() != null && existingOptions.containsKey(dto.getId()))
                    ? existingOptions.get(dto.getId())
                    : new Option();

            option.setText(dto.getText());
            option.setIsCorrect(dto.getIsCorrect());
            option.setQuestion(question);
            updatedOptions.add(option);
        }

        question.setOptions(updatedOptions);
    }
}