package com.shop.customer.domain.product.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "카테고리별 최저가 브랜드 및 총액 응답")
public record CategoryLowestPriceResponse(
    @Schema(description = "카테고리별 가격 정보 목록")
    List<CategoryPriceInfo> categories,

    @Schema(description = "총합 가격", example = "34100")
    int totalAmount
) {

    @Schema(description = "카테고리별 최저가 정보")
    public record CategoryPriceInfo(
        @Schema(description = "카테고리 이름", example = "상의")
        String category,

        @Schema(description = "브랜드 이름", example = "C")
        String brand,

        @Schema(description = "가격", example = "10000")
        int price
    ) {

    }
}
