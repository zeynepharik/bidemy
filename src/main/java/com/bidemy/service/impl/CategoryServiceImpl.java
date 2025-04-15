package com.bidemy.service.impl;

import com.bidemy.mapper.CategoryMapper;
import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.model.entity.Category;
import com.bidemy.repository.CategoryRepository;
import com.bidemy.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category Bulunamadı"));
        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOS.add(categoryMapper.toDTO(category));
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category Bulunamadı"));
        category.setName(dto.getName());
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category bulunamadı"));
        categoryRepository.delete(category);

    }
}
