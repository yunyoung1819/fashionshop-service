package com.shop.backoffice.domain.product.controller;

import com.shop.backoffice.domain.product.model.request.ProductRequest;
import com.shop.backoffice.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 추가 업데이트
     * @param productRequest
     * @return
     */
    @PostMapping("/product")
    @Operation(summary = "상품 등록", description = "Adds a new product")
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/product/{id}")
    @Operation(summary = "상품 수정", description = "Updates an existing product")
    public ResponseEntity<Void> modifyProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        productService.modifyProduct(id, productRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 상품 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/product/{id}")
    @Operation(summary = "상품 삭제", description = "Deletes an existing product")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
