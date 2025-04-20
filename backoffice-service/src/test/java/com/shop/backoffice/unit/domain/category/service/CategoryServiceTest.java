package com.shop.backoffice.unit.domain.category.service;

import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.category.service.CategoryService;
import com.shop.core.product.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 생성 성공 ‒ 저장된 엔티티의 이름이 올바른지 검증")
    void createCategorySuccess() {
        // given
        String name = "Accessories";
        Category saved = Category.builder().name(name).build();
        when(categoryRepository.save(any())).thenReturn(saved);

        // when
        Category result = categoryService.createCategory(name);

        // then
        assertNotNull(result);
        assertEquals(name, result.getName());

        // 저장 시 전달된 엔티티의 이름도 검증
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository, times(1)).save(captor.capture());
        assertEquals(name, captor.getValue().getName());
    }

    @Test
    @DisplayName("카테고리 수정 실패 ‒ 존재하지 않는 ID")
    void updateCategoryFailNotFound() {
        // given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
            categoryService.updateCategory(1L, "NewName")
        );
        assertTrue(ex.getMessage().contains("Category not found"));
    }

    @Test
    @DisplayName("카테고리 수정 성공 ‒ 이름이 변경되는지 검증")
    void updateCategorySuccess() {
        // given
        Category existing = Category.builder().name("OldName").build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));

        // when
        Category updated = categoryService.updateCategory(1L, "NewName");

        // then
        assertNotNull(updated);
        assertEquals("NewName", updated.getName());
        // save 호출은 없으므로 verify save never
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("카테고리 삭제 실패 ‒ 존재하지 않는 ID")
    void deleteCategoryFailNotFound() {
        // given
        when(categoryRepository.existsById(1L)).thenReturn(false);

        // when / then
        assertThrows(EntityNotFoundException.class, () ->
            categoryService.deleteCategoryById(1L)
        );
    }

    @Test
    @DisplayName("카테고리 삭제 성공 ‒ deleteById 호출 검증")
    void deleteCategorySuccess() {
        // given
        when(categoryRepository.existsById(1L)).thenReturn(true);

        // when
        categoryService.deleteCategoryById(1L);

        // then
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
