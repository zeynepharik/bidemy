package com.bidemy.mapper;

import com.bidemy.model.dto.ExamDTO;
import com.bidemy.model.dto.OptionDTO;
import com.bidemy.model.dto.QuestionDTO;
import com.bidemy.model.entity.Exam;
import com.bidemy.model.entity.Option;
import com.bidemy.model.entity.Question;
import com.bidemy.model.request.ExamRequest;
import com.bidemy.model.response.ExamResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(source = "lesson.id", target = "lessonId")
    ExamDTO toExamDTO(Exam exam);

    ExamDTO toExamDTO(ExamRequest request);

    Exam toExam(ExamRequest request);

    List<Exam> toEntities(List<ExamRequest> requests);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "options", target = "options")
    QuestionDTO toQuestionDTO(Question question);

    Question toQuestion(QuestionDTO questionDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "isCorrect", target = "isCorrect")
    OptionDTO toOptionDTO(Option option);

    Exam toExam(ExamDTO exam);

    List<Question> toQuestions(List<QuestionDTO> questionDTOS);

    List<QuestionDTO> toQuestionDTO(List<Question> questions);

    List<Option> toOptions(List<OptionDTO> optionDTOS);

    List<OptionDTO> toOptionDTO(List<Option> options);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "questionsList", target = "questionsList")
    ExamResponse toExamResponse(Exam exam);

    @AfterMapping
    default void linkQuestions(Exam exam) {
        if (exam.getQuestionsList() != null) {
            for (Question question : exam.getQuestionsList()) {
                question.setExam(exam);
                if (question.getOptions() != null) {
                    for (Option option : question.getOptions()) {
                        option.setQuestion(question);
                    }
                }
            }
        }
    }
}
