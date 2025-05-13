package com.bidemy.mapper;

import com.bidemy.model.entity.Category;
import com.bidemy.model.request.CategoryRequest;
import com.bidemy.model.response.CategoryResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}
