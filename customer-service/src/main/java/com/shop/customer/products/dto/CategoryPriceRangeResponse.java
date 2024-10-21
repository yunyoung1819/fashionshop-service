package com.shop.customer.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
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
    public static class PriceInfo {
        private String brand;
        private int price;

        public PriceInfo(String brand, int price) {
            this.brand = brand;
            this.price = price;
        }
    }
}
