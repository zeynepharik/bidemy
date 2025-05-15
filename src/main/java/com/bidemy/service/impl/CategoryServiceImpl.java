package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.CategoryMapper;
import com.bidemy.model.entity.Category;
import com.bidemy.model.entity.Course;
import com.bidemy.model.request.CategoryRequest;
import com.bidemy.model.response.CategoryResponse;
import com.bidemy.repository.CategoryRepository;
import com.bidemy.repository.CourseRepository;
import com.bidemy.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public CategoryResponse create(CategoryRequest request) {
        Category category = this.categoryMapper.toEntity(request);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));

        category.setName(request.getName());

        for(Course course : courseRepository.findByCategoryId(id)) {
            course.setCategory(category);
            this.courseRepository.save(course);
        }

        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.CATEGORY_NOT_FOUND));

        for(Course course : courseRepository.findByCategoryId(id)) {
            course.setCategory(null);
            this.courseRepository.save(course);
        }

        this.categoryRepository.delete(category);
    }
}
