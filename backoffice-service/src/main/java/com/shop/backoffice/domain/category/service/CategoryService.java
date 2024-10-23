package com.shop.backoffice.domain.category.service;

import com.shop.backoffice.domain.category.model.request.CategoryRequest;

import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.name())
                .build();

        categoryRepository.save(category);
    }

    @Transactional
    public void modifyCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Category changedCategory = category.toBuilder()
                .name(categoryRequest.name())
                .build();

        category.update(changedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        categoryRepository.deleteById(id);
    }
}
