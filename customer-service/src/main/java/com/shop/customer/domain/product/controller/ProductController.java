package com.shop.customer.domain.product.controller;

import com.shop.customer.domain.product.model.response.CategoryLowestPriceResponse;
import com.shop.customer.domain.product.model.response.CategoryPriceRangeResponse;
import com.shop.customer.domain.product.model.response.LowestPriceResponse;
import com.shop.customer.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 관련 API")
@RestController
@RequestMapping("/v1/customer/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 1. 카테고리별 최저가 상품과 브랜드, 총 금액을 조회합니다.
     *
     * @return 카테고리별 최저가 상품 정보 + 총액
     */
    @GetMapping("/category/lowest-price")
    @Operation(summary = "카테고리 별 최저가격 조회", description = "각 카테고리에서 가장 저렴한 상품의 브랜드와 가격을 반환합니다. 총 구매 금액도 함께 제공됩니다.")
    public ResponseEntity<CategoryLowestPriceResponse> getLowestPriceByCategory() {
        CategoryLowestPriceResponse response = productService.getLowestPriceByCategory();
        return ResponseEntity.ok(response);
    }

    /**
     * 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
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
    public ResponseEntity<CategoryPriceRangeResponse> getPriceRange(
        @RequestParam String categoryName) {
        CategoryPriceRangeResponse response = productService.getPriceRangeByCategoryName(
            categoryName);
        return ResponseEntity.ok(response);
    }
}
