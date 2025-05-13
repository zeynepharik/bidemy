package com.bidemy.mapper;

import com.bidemy.model.entity.Lesson;
import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.response.LessonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(source = "exams", target = "exams")
    Lesson toEntity(LessonRequest lessonRequest);

    @Mapping(source = "exams", target = "exams")
    LessonResponse toResponse(Lesson lesson);

    List<Lesson> toLessonEntities(List<LessonRequest> lessonRequests);
}
