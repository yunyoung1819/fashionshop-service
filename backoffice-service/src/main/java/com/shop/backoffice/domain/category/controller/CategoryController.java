package com.shop.backoffice.domain.category.controller;

import com.shop.backoffice.domain.category.dto.request.CategoryRequest;
import com.shop.backoffice.domain.category.dto.response.CategoryResponse;
import com.shop.backoffice.domain.category.service.CategoryService;
import com.shop.backoffice.exception.model.response.ApiResponse;
import com.shop.core.product.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 추가 API
     *
     */
    @PostMapping("")
    @Operation(summary = "카테고리 등록", description = "Adds a new category")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
        @RequestBody @Valid CategoryRequest request) {
        String categoryName = request.name();
        Category created = categoryService.createCategory(categoryName);
        CategoryResponse response = CategoryResponse.from(created);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("SUCCESS", response));
    }

    /**
     * 카테고리 수정 API
     *
     */
    @PatchMapping("/{id}")
    @Operation(summary = "카테고리 수정", description = "Updates an existing category")
    public ResponseEntity<ApiResponse<CategoryResponse>> modifyCategory(@PathVariable Long id,
        @RequestBody @Valid CategoryRequest request) {
        String newName = request.name();
        Category updated = categoryService.updateCategory(id, newName);
        CategoryResponse response = CategoryResponse.from(updated);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", response));
    }

    /**
     * 카테고리 삭제 API
     *
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "카테고리 삭제", description = "Deletes an existing category")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted", null));
    }
}
