package com.bidemy.mapper;

import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "instructor.id", target = "instructorId")
    CourseDTO toDto(Course course);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "instructorId", target = "instructor.id")
    Course toEntity(CourseDTO courseDTO);
}
