package com.bidemy.mapper;

import com.bidemy.model.entity.Lesson;
import com.bidemy.model.entity.Section;
import com.bidemy.model.request.LessonRequest;
import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.SectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(source = "courseId", target = "course.id")
    Section toEntity(SectionRequest request);

    @Mapping(source = "course.id", target = "courseId")
    SectionResponse toResponse(Section section);

    List<Lesson> toLessonEntities(List<LessonRequest> lessonRequests);
}
