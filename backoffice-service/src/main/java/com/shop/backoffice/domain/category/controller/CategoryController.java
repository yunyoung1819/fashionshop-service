package com.shop.backoffice.domain.category.controller;

import com.shop.backoffice.domain.category.dto.request.CategoryRequest;
import com.shop.backoffice.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 추가 API
     * @param categoryRequest
     * @return
     */
    @PostMapping("/category")
    @Operation(summary = "카테고리 등록", description = "Adds a new category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            categoryService.createCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category successfully created.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create category: " + e.getMessage());
        }
    }

    /**
     * 카테고리 수정 API
     * @param id
     * @param categoryRequest
     * @return
     */
    @PatchMapping("/category/{id}")
    @Operation(summary = "카테고리 수정", description = "Updates an existing category")
    public ResponseEntity<?> modifyCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        try {
            categoryService.modifyCategory(id, categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Category successfully updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update category: " + e.getMessage());
        }
    }

    /**
     * 카테고리 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/category/{id}")
    @Operation(summary = "카테고리 삭제", description = "Deletes an existing category")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body("Category successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete category: " + e.getMessage());
        }
    }
}
