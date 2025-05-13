package com.bidemy.mapper;

import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.entity.Course;
import com.bidemy.model.request.CourseRequest;
import com.bidemy.model.response.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(target = "sections.lessonList", ignore = true)
    CourseDTO toDto(Course course);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "sections.lessonList", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(target = "sections.lessonList", ignore = true)
    CourseResponse toResponse(Course course);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(target = "sections.lessonList", ignore = true)
    CourseRequest toRequest(Course course);



}
