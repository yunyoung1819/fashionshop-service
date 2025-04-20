package com.shop.backoffice.unit.domain.product.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.service.ProductService;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.product.entity.Brand;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 생성 성공")
    void createProductSuccess() {
        // given
        Brand b = Brand.builder().name("A").build();
        Category c = Category.builder().name("상의").build();
        when(brandRepository.findById(1L)).thenReturn(Optional.of(b));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(c));
        Product saved = Product.builder().brand(b).category(c).price(1000).build();
        when(productRepository.save(any())).thenReturn(saved);

        // when
        Product result = productService.createProduct(1L, 2L, 1000);

        // then
        assertNotNull(result);
        assertEquals(1000, result.getPrice());
        ArgumentCaptor<Product> cap = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(cap.capture());
        assertEquals(b, cap.getValue().getBrand());
        assertEquals(c, cap.getValue().getCategory());
    }

    @Test
    @DisplayName("상품 생성 실패 - 브랜드 없음")
    void createProductFailBrand() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
            productService.createProduct(1L, 2L, 1000)
        );
    }

    @Test
    @DisplayName("상품 생성 실패 - 카테고리 없음")
    void createProductFailCategory() {
        Brand b = Brand.builder().name("A").build();
        when(brandRepository.findById(1L)).thenReturn(Optional.of(b));
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
            productService.createProduct(1L, 2L, 1000)
        );
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProductSuccess() {
        Brand b = Brand.builder().name("A").build();
        Category c = Category.builder().name("하의").build();
        Product existing = Product.builder().brand(b).category(c).price(500).build();

        when(brandRepository.findById(1L)).thenReturn(Optional.of(b));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(c));
        when(productRepository.findById(10L)).thenReturn(Optional.of(existing));

        Product updated = productService.updateProduct(10L, 1L, 2L, 1500);

        assertEquals(1500, updated.getPrice());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("상품 수정 실패 - 상품 없음")
    void updateProductFailNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(Brand.builder().name("A").build()));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(Category.builder().name("상의").build()));
        when(productRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
            productService.updateProduct(10L, 1L, 2L, 1500)
        );
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProductSuccess() {
        when(productRepository.existsById(5L)).thenReturn(true);
        productService.deleteProductById(5L);
        verify(productRepository).deleteById(5L);
    }

    @Test
    @DisplayName("상품 삭제 실패 - 상품 없음")
    void deleteProductFailNotFound() {
        when(productRepository.existsById(5L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () ->
            productService.deleteProductById(5L)
        );
    }
}
