package com.bidemy.mapper;

import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(source = "category.id", target = "categoryId")
    CategoryDTO toDTO(Category category);

    @Mapping(source = "categoryId", target = "category.id")
    Category toEntity(CategoryDTO categoryDTO);
}
