package com.shop.customer.domain.product.service;

import com.shop.core.product.entity.Brand;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
import com.shop.customer.domain.category.repository.CategoryReadRepository;
import com.shop.customer.domain.product.model.response.CategoryLowestPriceResponse;
import com.shop.customer.domain.product.model.response.CategoryPriceRangeResponse;
import com.shop.customer.domain.product.model.response.LowestPriceResponse;
import com.shop.customer.domain.product.repository.ProductReadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductReadRepository productReadRepository;

    @Mock
    private CategoryReadRepository categoryReadRepository;

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

        assertEquals("A", response.categories().get(0).brand());
        assertEquals("상의", response.categories().get(0).category());
        assertEquals(10000, response.categories().get(0).price());
        assertEquals(10000, response.totalAmount());
    }

    @Test
    @DisplayName("카테고리별 최저가격 조회 실패 테스트 - 데이터 없음")
    void getLowestPriceByCategory_fail() {
        when(productReadRepository.findLowestPricedProductsByCategory()).thenReturn(Collections.emptyList());

        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();

        assertEquals(0, response.categories().size());
        assertEquals(0, response.totalAmount());
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
        assertEquals("A", response.lowestPrice().brand());
        assertEquals(300000, response.lowestPrice().totalAmount());
        assertEquals(2, response.lowestPrice().categories().size());

        assertEquals("상의", response.lowestPrice().categories().get(0).category());
        assertEquals(100000, response.lowestPrice().categories().get(0).price());

        assertEquals("바지", response.lowestPrice().categories().get(1).category());
        assertEquals(200000, response.lowestPrice().categories().get(1).price());
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
        Category top = Category.builder().name("상의").build();

        Product lowestPriceProduct = Product.builder()
                .price(10000)
                .brand(brandA)
                .category(top)
                .build();

        Product highestPriceProduct = Product.builder()
                .price(20000)
                .brand(brandB)
                .category(top)
                .build();

        when(categoryReadRepository.findByName("상의")).thenReturn(Optional.of(top));
        when(productReadRepository.findLowestPricedProductsByCategoryName("상의")).thenReturn(List.of(lowestPriceProduct));
        when(productReadRepository.findHighestPricedProductsByCategoryName("상의")).thenReturn(List.of(highestPriceProduct));

        CategoryPriceRangeResponse response = productService.getPriceRangeByCategoryName("상의");

        assertNotNull(response);
        assertEquals("상의", response.category());

        // 최저가 검증
        assertEquals(1, response.lowestPrice().size());
        assertEquals("A", response.lowestPrice().get(0).brand());
        assertEquals(10000, response.lowestPrice().get(0).price());

        // 최고가 검증
        assertEquals(1, response.highestPrice().size());
        assertEquals("B", response.highestPrice().get(0).brand());
        assertEquals(20000, response.highestPrice().get(0).price());
    }

    @Test
    @DisplayName("카테고리 최저, 최고 가격 조회 실패 테스트 - 존재하지 않는 카테고리")
    void getPriceRangeByCategoryName_fail_categoryNotFound() {
        when(categoryReadRepository.findByName("empty")).thenReturn(Optional.empty());

        // 메서드 실행 및 예외 검증
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getPriceRangeByCategoryName("empty");
        });

        assertEquals("Category not found: empty", exception.getMessage());
    }
}