package com.shop.backoffice.unit.domain.product.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.service.ProductService;
import com.shop.backoffice.domain.product.dto.request.ProductRequest;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
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
    @DisplayName("상품 생성 테스트")
    void createProductSuccess() {
        Brand brand = Brand.builder()
                .name("A")
                .build();

        Category category = Category.builder()
                .name("상의")
                .build();

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        ProductRequest request = new ProductRequest(1L, 2L, 1000);

        productService.createProduct(request);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 생성 테스트 - 브랜드 없음")
    public void createProductFailBrandNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRequest request = new ProductRequest(1L, 1L, 1000);

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("상품 생성 테스트 - 카테고리 없음")
    public void createProductFailCategoryNotFound() {
        Brand brand = Brand.builder()
                .name("A")
                .build();

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRequest request = new ProductRequest(1L, 1L, 1000);

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void modifyProductSuccess() {
        Brand brand = Brand.builder()
                .name("A")
                .build();

        Category category = Category.builder()
                .name("상의")
                .build();

        Product existingProduct = Product.builder()
                .brand(brand)
                .category(category)
                .price(10000)
                .build();

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));

        ProductRequest request = new ProductRequest(1L, 2L, 15000);

        productService.modifyProduct(1L, request);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertEquals(brand, savedProduct.getBrand());
        assertEquals(category, savedProduct.getCategory());
        assertEquals(15000, savedProduct.getPrice());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductSuccess() {
        Product existingProduct = new Product(new Brand("Nike"), new Category("Shoes"), 1500);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("상품 삭제 테스트 - 상품 없음")
    public void deleteProductFail() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(1L));
    }
}
