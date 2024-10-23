package com.shop.backoffice.unit.domain.category.service;

import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.category.service.CategoryService;
import com.shop.backoffice.domain.category.dto.request.CategoryRequest;
import com.shop.core.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 생성 테스트")
    void createCategorySuccess() {
        CategoryRequest request = new CategoryRequest("Accessories");

        categoryService.createCategory(request);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 수정 테스트 - 카테고리 없음")
    public void modifyCategoryFailCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CategoryRequest request = new CategoryRequest("Accessories");

        assertThrows(IllegalArgumentException.class, () -> categoryService.modifyCategory(1L, request));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트 - 카테고리 없음")
    public void deleteCategoryFail() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> categoryService.deleteCategory(1L));
    }
}
