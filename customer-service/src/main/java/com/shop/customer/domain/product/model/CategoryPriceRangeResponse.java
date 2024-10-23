package com.shop.customer.domain.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CategoryPriceRangeResponse {
    private String category;
    private List<PriceInfo> lowestPrice;
    private List<PriceInfo> highestPrice;

    public CategoryPriceRangeResponse(String category, List<PriceInfo> lowestPrice, List<PriceInfo> highestPrice) {
        this.category = category;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    @Getter
    @NoArgsConstructor
    public static class PriceInfo {
        private String brand;
        private int price;

        public PriceInfo(String brand, int price) {
            this.brand = brand;
            this.price = price;
        }
    }
}
