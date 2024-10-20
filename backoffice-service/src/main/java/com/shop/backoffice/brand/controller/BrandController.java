package com.shop.backoffice.brand.controller;

import com.shop.backoffice.brand.dto.BrandRequest;
import com.shop.backoffice.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backoffice")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;


    /**
     * 브랜드 추가 API
     * @param brandRequest
     * @return
     */
    @PostMapping("/brand")
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
