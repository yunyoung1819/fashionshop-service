package com.shop.customer.domain.products.service;

import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import com.shop.customer.products.dto.CategoryLowestPriceResponse;
import com.shop.customer.products.dto.LowestPriceResponse;
import com.shop.customer.products.repository.ProductReadRepository;
import com.shop.customer.products.service.ProductService;
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
    void getLowestPriceByCategory_success() {
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
        assertEquals(10000, response.getTotalAmount());
    }

    @Test
    @DisplayName("카테고리별 최저가격 조회 실패 테스트 - 데이터 없음")
    void getLowestPriceByCategory_fail() {
        when(productReadRepository.findLowestPricedProductsByCategory()).thenReturn(Collections.emptyList());

        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();

        assertEquals(0, response.getCategories().size());
        assertEquals(0, response.getTotalAmount());
    }

    @Test
    @DisplayName("단일 브랜드 최저가 조회 성공 테스트 - 브랜드 A가 최저가일 때")
    void getLowestPriceBrand_success() {
        Brand brandA = Brand.builder().name("A").build();
        Brand brandB = Brand.builder().name("B").build();

        Category top = Category.builder().name("상의").build();
        Category pants = Category.builder().name("바지").build();

        // 브랜드 A의 제품
        Product productA1 = Product.builder()
                .price(100000)
                .brand(brandA)
                .category(top)
                .build();

        Product productA2 = Product.builder()
                .price(200000)
                .brand(brandA)
                .category(pants)
                .build();

        // 브랜드 B의 제품
        Product productB1 = Product.builder()
                .price(300000)
                .brand(brandB)
                .category(top)
                .build();

        Product productB2 = Product.builder()
                .price(400000)
                .brand(brandB)
                .category(pants)
                .build();

        when(productReadRepository.findLowestTotalPriceByBrand()).thenReturn(List.of(new Object[]{"A", 300000}, new Object[]{"B", 700000}));
        when(productReadRepository.findByBrandName("A")).thenReturn(List.of(productA1, productA2));
        when(productReadRepository.findByBrandName("B")).thenReturn(List.of(productB1, productB2));

        LowestPriceResponse response = productService.getLowestPriceBrand();

        assertNotNull(response);
        assertEquals("A", response.getLowestPrice().getBrand());
        assertEquals(300000, response.getLowestPrice().getTotalAmount());
        assertEquals(2, response.getLowestPrice().getCategories().size());

        assertEquals("상의", response.getLowestPrice().getCategories().get(0).getCategory());
        assertEquals(100000, response.getLowestPrice().getCategories().get(0).getPrice());

        assertEquals("바지", response.getLowestPrice().getCategories().get(1).getCategory());
        assertEquals(200000, response.getLowestPrice().getCategories().get(1).getPrice());
    }

    @Test
    @DisplayName("단일 브랜드 최저가 조회 실패 테스트 - 데이터 없음")
    void getLowestPriceBrand_fail() {
        when(productReadRepository.findLowestTotalPriceByBrand()).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getLowestPriceBrand());

        assertEquals("No brands found.", exception.getMessage());
    }

    @Test
    @DisplayName("카테고리 최저, 최고 가격 조회 성공 테스트 - 카테고리 '상의'에 대한 가격 범위 조회")
    void getPriceRangeByCategoryName_success() {
        // Mock 데이터 생성
        Brand brandA = Brand.builder().name("A").build();
        Brand brandB = Brand.builder().name("B").build();
        Category category = Category.builder().name("상의").build();

        Product lowestPriceProduct = Product.builder()
                .price(10000)
                .brand(brandA)
                .category(category)
                .build();

        Product highestPriceProduct = Product.builder()
                .price(20000)
                .brand(brandB)
                .category(category)
                .build();

        // Mock 설정
        when(categoryReadRepository.findByName("상의")).thenReturn(Optional.of(category));
        when(productReadRepository.findLowestPricedProductsByCategoryName("상의")).thenReturn(List.of(lowestPriceProduct));
        when(productReadRepository.findHighestPricedProductsByCategoryName("상의")).thenReturn(List.of(highestPriceProduct));

        // 메서드 실행
        CategoryPriceRangeResponse response = productService.getPriceRangeByCategoryName("상의");

        // 결과 검증
        assertNotNull(response);
        assertEquals("상의", response.getCategory());

        // 최저가 검증
        assertEquals(1, response.getLowestPrice().size());
        assertEquals("A", response.getLowestPrice().get(0).getBrand());
        assertEquals(10000, response.getLowestPrice().get(0).getPrice());

        // 최고가 검증
        assertEquals(1, response.getHighestPrice().size());
        assertEquals("B", response.getHighestPrice().get(0).getBrand());
        assertEquals(20000, response.getHighestPrice().get(0).getPrice());
    }

    @Test
    @DisplayName("카테고리 최저, 최고 가격 조회 실패 테스트 - 존재하지 않는 카테고리")
    void getPriceRangeByCategoryName_fail_categoryNotFound() {
        // Mock 설정: 카테고리가 존재하지 않는 경우
        when(categoryReadRepository.findByName("없는카테고리")).thenReturn(Optional.empty());

        // 메서드 실행 및 예외 검증
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getPriceRangeByCategoryName("없는카테고리");
        });

        assertEquals("Category not found: 없는카테고리", exception.getMessage());
    }
}