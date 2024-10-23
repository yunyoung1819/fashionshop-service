package com.shop.backoffice.domain.brand.controller;

import com.shop.backoffice.domain.brand.dto.request.BrandRequest;
import com.shop.backoffice.domain.brand.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    /**
     * 브랜드 추가 API
     * @param brandRequest
     * @return
     */
    @PostMapping("/brand")
    @Operation(summary = "브랜드 등록", description = "Adds a new brand")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest) {
        try {
            brandService.addBrand(brandRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Brand added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add brand: " + e.getMessage());
        }
    }

    /**
     * 브랜드 업데이트 API
     * @param id
     * @param brandRequest
     * @return
     */
    @PatchMapping("/brand/{id}")
    @Operation(summary = "브랜드 수정", description = "Updates an existing brand")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {
        try {
            brandService.updateBrand(id, brandRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Brand updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update brand: " + e.getMessage());
        }
    }

    /**
     * 브랜드 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/brand/{id}")
    @Operation(summary = "브랜드 삭제", description = "Deletes an existing brand")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrand(id);
            return ResponseEntity.status(HttpStatus.OK).body("Brand deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete brand: " + e.getMessage());
        }
    }
}
