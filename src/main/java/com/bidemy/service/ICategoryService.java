package com.bidemy.service;

import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.request.CategoryRequest;
import com.bidemy.model.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse create(CategoryRequest request);

    CategoryResponse getById(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}
