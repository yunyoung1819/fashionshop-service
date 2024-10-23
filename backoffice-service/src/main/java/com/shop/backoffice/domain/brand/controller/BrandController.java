package com.shop.backoffice.domain.brand.controller;

import com.shop.backoffice.domain.brand.model.request.BrandRequest;
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
    public ResponseEntity<Void> addBrand(@RequestBody BrandRequest brandRequest) {
        brandService.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 브랜드 업데이트 API
     * @param id
     * @param brandRequest
     * @return
     */
    @PatchMapping("/brand/{id}")
    @Operation(summary = "브랜드 수정", description = "Updates an existing brand")
    public ResponseEntity<Void> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {
        brandService.updateBrand(id, brandRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 브랜드 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/brand/{id}")
    @Operation(summary = "브랜드 삭제", description = "Deletes an existing brand")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
