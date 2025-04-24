package com.bidemy.mapper;

import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.entity.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
