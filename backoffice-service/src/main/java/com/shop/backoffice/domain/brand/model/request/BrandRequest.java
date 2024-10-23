package com.shop.backoffice.domain.brand.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "브랜드 요청")
public record BrandRequest(
        @Schema(description = "브랜드명", example = "나이키")
        @NotNull
        String name
){}
