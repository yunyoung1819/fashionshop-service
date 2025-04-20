package com.shop.backoffice.domain.product.controller;

import com.shop.backoffice.domain.product.dto.request.ProductRequest;
import com.shop.backoffice.domain.product.dto.request.ProductResponse;
import com.shop.backoffice.domain.product.service.ProductService;
import com.shop.backoffice.exception.model.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 추가 업데이트
     */
    @PostMapping
    @Operation(summary = "상품 등록", description = "새 상품을 추가하고 생성된 DTO를 반환합니다.")
    public ResponseEntity<ApiResponse<ProductResponse>> create(
        @RequestBody ProductRequest req
    ) {
        var created = productService.createProduct(
            req.brandId(), req.categoryId(), req.price()
        );
        var dto = ProductResponse.from(created);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Product created", dto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "상품 수정", description = "기존 상품을 수정하고 수정된 DTO를 반환합니다.")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
        @PathVariable Long id,
        @RequestBody ProductRequest req
    ) {
        var updated = productService.updateProduct(
            id, req.brandId(), req.categoryId(), req.price()
        );
        var dto = ProductResponse.from(updated);
        return ResponseEntity
            .ok(ApiResponse.success("Product updated", dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "지정한 ID의 상품을 삭제하고, 성공 여부를 반환합니다.")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity
            .ok(ApiResponse.success("Product deleted", null));
    }
}
