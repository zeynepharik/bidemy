package com.bidemy.controller.impl;

import com.bidemy.controller.ICategoryController;
import com.bidemy.model.dto.CategoryDTO;
import com.bidemy.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryControllerImpl implements ICategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    @Override
    public CategoryDTO create(@RequestBody CategoryDTO dto) {
        return categoryService.create(dto);
    }

    @GetMapping("/{id}")
    @Override
    public CategoryDTO getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    @Override
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @PutMapping("/{id}")
    @Override
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
