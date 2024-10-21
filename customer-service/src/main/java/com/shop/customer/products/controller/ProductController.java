package com.shop.customer.products.controller;

import com.shop.customer.exception.dto.response.ErrorResponse;
import com.shop.customer.products.dto.CategoryLowestPriceResponse;
import com.shop.customer.products.dto.CategoryPriceRangeResponse;
import com.shop.customer.products.dto.LowestPriceResponse;
import com.shop.customer.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     *
     * @return ResponseEntity<?>
     */
    @GetMapping("/category/lowest-price")
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
     * 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     *
     * @return ResponseEntity<?>
     */
    @GetMapping("/brand/lowest-price")
    public ResponseEntity<?> getLowestPriceBrand() {
        try {
            LowestPriceResponse response = productService.getLowestPriceBrand();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to retrieve data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    /**
     * 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     *
     * @param categoryName
     * @return ResponseEntity<?>
     */
    @GetMapping("/category/price-range")
    public ResponseEntity<?> getPriceRange(@RequestParam String categoryName) {
        try {
            CategoryPriceRangeResponse response = productService.getPriceRangeByCategoryName(categoryName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Failed to retrieve data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
