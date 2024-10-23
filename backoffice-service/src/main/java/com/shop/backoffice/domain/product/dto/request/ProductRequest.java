package com.shop.backoffice.domain.product.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 요청")
public record ProductRequest(
        @Schema(description = "상품명", example = "나이키 운동화")
        Long brandId,

        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId,

        @Schema(description = "상품가격", example = "10000")
        int price
) {}