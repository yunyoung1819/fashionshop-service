package com.shop.customer.domain.product.model.response;

import java.util.List;


public record LowestPriceResponse(
    LowestBrandInfo lowestPrice
) {
    public record LowestBrandInfo(
        String brand,
        List<CategoryPrice> categories,
        int totalAmount
    ) {
        public record CategoryPrice(
            String category,
            int price
        ) { }
    }
}
