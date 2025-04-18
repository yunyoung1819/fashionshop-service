package com.shop.customer.domain.product.model.response;

import java.util.List;

public record CategoryPriceRangeResponse(
    String category,
    List<PriceInfo> lowestPrice,
    List<PriceInfo> highestPrice
) {
    public record PriceInfo(
        String brand,
        int price
    ) {}
}
