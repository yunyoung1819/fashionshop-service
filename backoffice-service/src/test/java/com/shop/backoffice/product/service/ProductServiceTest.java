package com.shop.backoffice.product.service;

import com.shop.backoffice.brand.repository.BrandRepository;
import com.shop.backoffice.category.repository.CategoryRepository;
import com.shop.backoffice.product.dto.request.ProductRequest;
import com.shop.backoffice.product.repository.ProductRepository;
import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("상품 생성 테스트")
    void createProduct_success() {
        Brand brand = Brand.builder()
                .name("A")
                .build();

        Category category = Category.builder()
                .name("상의")
                .build();

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        ProductRequest productRequest = new ProductRequest(1L, 2L, 1000);

        productService.createProduct(productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void modifyProduct_success() {
        // Arrange
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

        ProductRequest productRequest = new ProductRequest(1L, 2L, 15000);

        productService.modifyProduct(1L, productRequest);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertEquals(brand, savedProduct.getBrand());
        assertEquals(category, savedProduct.getCategory());
        assertEquals(15000, savedProduct.getPrice());
    }


    @Test
    @DisplayName("상품 삭제 테스트")
    void removeProduct_success() {
        Product existingProduct = Product.builder()
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}