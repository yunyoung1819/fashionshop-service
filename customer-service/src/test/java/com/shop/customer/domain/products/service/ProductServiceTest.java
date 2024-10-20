package com.shop.customer.domain.products.service;

import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import com.shop.customer.domain.products.dto.CategoryLowestPriceResponse;
import com.shop.customer.domain.products.repository.ProductReadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductReadRepository productReadRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("카테고리별 최저가격 조회 성공 테스트 - 두 개의 데이터 중 최저값 조회")
    void getLowestPriceCategory_success() {
        Brand brandA = Brand.builder()
                .name("A")
                .build();

        Brand brandB = Brand.builder()
                .name("B")
                .build();

        Category outer = Category.builder()
                .name("상의")
                .build();

        Product productA = Product.builder()
                .price(10000)
                .brand(brandA)
                .category(outer)
                .build();

        Product productB = Product.builder()
                .price(20000)
                .brand(brandB)
                .category(outer)
                .build();

        when(productReadRepository.findLowestPricedProductsByCategory()).thenReturn(List.of(productA, productB));

        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();

        assertEquals("A", response.getCategories().get(0).getBrand());
        assertEquals("상의", response.getCategories().get(0).getCategory());
        assertEquals(10000, response.getCategories().get(0).getPrice());
        assertEquals(30000, response.getTotalAmount());
    }

    @Test
    @DisplayName("카테고리별 최저가격 조회 실패 테스트 - 데이터 없음")
    void getLowestPriceCategory_fail_nodata() {
        when(productReadRepository.findLowestPricedProductsByCategory()).thenReturn(Collections.emptyList());

        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();

        assertEquals(0, response.getCategories().size());
        assertEquals(0, response.getTotalAmount());
    }

}