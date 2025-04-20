package com.shop.backoffice.domain.brand.controller;

import com.shop.backoffice.domain.brand.dto.request.BrandRequest;
import com.shop.backoffice.domain.brand.dto.response.BrandResponse;
import com.shop.backoffice.domain.brand.service.BrandService;
import com.shop.backoffice.exception.model.response.ApiResponse;
import com.shop.core.product.entity.Brand;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/backoffice/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    /**
     * 브랜드 추가 API
     */
    @PostMapping
    @Operation(summary = "브랜드 등록", description = "새 브랜드를 추가하고 생성된 DTO를 반환합니다.")
    public ResponseEntity<ApiResponse<BrandResponse>> create(@RequestBody BrandRequest request) {
        Brand created = brandService.createBrand(request.name());
        BrandResponse response = BrandResponse.from(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Brand created", response));
    }

    /**
     * 브랜드 업데이트 API
     */
    @PatchMapping("/{id}")
    @Operation(summary = "브랜드 수정", description = "기존 브랜드를 수정하고, 수정된 DTO를 반환합니다.")
    public ResponseEntity<ApiResponse<BrandResponse>> update(
        @PathVariable Long id,
        @RequestBody BrandRequest request
    ) {
        Brand updated = brandService.updateBrandName(id, request.name());
        BrandResponse response     = BrandResponse.from(updated);
        return ResponseEntity
            .ok(ApiResponse.success("Brand updated", response));
    }

    /**
     * 브랜드 삭제 API
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "브랜드 삭제", description = "지정한 ID의 브랜드를 삭제하고, 성공 여부를 반환합니다.")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        brandService.deleteBrandById(id);
        return ResponseEntity
            .ok(ApiResponse.success("Brand deleted", null));
    }
}
