package com.shop.customer.domain.products.controller;

import com.shop.customer.common.exception.dto.ErrorResponse;
import com.shop.customer.domain.products.dto.CategoryLowestPriceResponse;
import com.shop.customer.domain.products.dto.CategoryPriceRangeResponse;
import com.shop.customer.domain.products.dto.LowestPriceResponse;
import com.shop.customer.domain.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping("/lowest-price/category")
    public ResponseEntity<?> getLowestPriceByCategory() {
        try {
            CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to retrieve data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    /**
     * 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때
     * 최저가격에 판매하는 브랜드와 카테고리의 상품 가격, 총액을 조회하는 API
     *
     */
    @GetMapping("/lowest-price/brand")
    public ResponseEntity<?> getLowestPriceByBrand() {
        try {
            LowestPriceResponse response = productService.getLowestPriceByBrand();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to retrieve data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    /**
     * 3. 특정 카테고리에서 최저가격 브랜드와 최고 가격 브랜드를 조회하는 API
     */
    @GetMapping("/price-range")
    public ResponseEntity<?> getPriceRangeByCategory(@RequestParam String categoryName) {
        try {
            CategoryPriceRangeResponse response = productService.getPriceRangeByCategory(categoryName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to retrieve data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
