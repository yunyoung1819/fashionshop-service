package com.shop.customer.domain.product.controller;

import com.shop.customer.domain.product.model.CategoryLowestPriceResponse;
import com.shop.customer.domain.product.model.CategoryPriceRangeResponse;
import com.shop.customer.domain.product.model.LowestPriceResponse;
import com.shop.customer.exception.model.response.ErrorResponse;
import com.shop.customer.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customer/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     *
     * @return ResponseEntity<?>
     */
    @GetMapping("/category/lowest-price")
    @Operation(summary = "카테고리 별 최저가격 조회", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    public ResponseEntity<CategoryLowestPriceResponse> getLowestPriceByCategory() {
        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();
        return ResponseEntity.ok(response);
    }

    /**
     * 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때
     * 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     *
     * @return ResponseEntity<?>
     */
    @GetMapping("/brand/lowest-price")
    @Operation(summary = "브랜드 별 최저가격 조회", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    public ResponseEntity<LowestPriceResponse> getLowestPriceBrand() {
        LowestPriceResponse response = productService.getLowestPriceBrand();
        return ResponseEntity.ok(response);
    }

    /**
     * 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     *
     * @param categoryName
     * @return ResponseEntity<?>
     */
    @GetMapping("/category/price-range")
    @Operation(summary = "카테고리 별 가격 범위 조회", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    public ResponseEntity<CategoryPriceRangeResponse> getPriceRange(@RequestParam String categoryName) {
        CategoryPriceRangeResponse response = productService.getPriceRangeByCategoryName(categoryName);
        return ResponseEntity.ok(response);
    }
}
