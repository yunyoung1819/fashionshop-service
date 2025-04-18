package com.shop.backoffice.domain.category.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "카테고리 요청")
public record CategoryRequest(
    @Schema(description = "카테고리명", example = "상의")
    @NotNull
    String name
) {

}