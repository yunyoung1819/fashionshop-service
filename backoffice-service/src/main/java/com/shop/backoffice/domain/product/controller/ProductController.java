package com.shop.backoffice.domain.product.controller;

import com.shop.backoffice.domain.product.dto.request.ProductRequest;
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
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.createProduct(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product successfully created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create product: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create product: " + e.getMessage());
        }
    }

    @PatchMapping("/product/{id}")
    @Operation(summary = "상품 수정", description = "Updates an existing product")
    public ResponseEntity<String> modifyProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        try {
            productService.modifyProduct(id, productRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Product successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update product: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update product: " + e.getMessage());
        }
    }

    /**
     * 상품 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/product/{id}")
    @Operation(summary = "상품 삭제", description = "Deletes an existing product")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete product: " + e.getMessage());
        }
    }
}
