package com.shop.customer.domain.product.model.response;

import java.util.List;

public record BrandPriceInfo(
    String brand,
    List<CategoryPrice> categories,
    int totalAmount
) {
    public record CategoryPrice(
        String category,
        int price
    ) { }
}