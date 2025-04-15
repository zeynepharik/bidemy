package com.bidemy.service;

import com.bidemy.model.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    CategoryDTO create(CategoryDTO dto);
    CategoryDTO getById(Long id);
    List<CategoryDTO> getAll();
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
}
