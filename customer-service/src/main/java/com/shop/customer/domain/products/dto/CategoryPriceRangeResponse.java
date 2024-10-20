package com.shop.customer.domain.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CategoryPriceRangeResponse {
    private String category;
    private List<PriceInfo> lowestPrice;
    private List<PriceInfo> highestPrice;
    private String error;
    private String errorMessage;

    public CategoryPriceRangeResponse(String category, List<PriceInfo> lowestPrice, List<PriceInfo> highestPrice) {
        this.category = category;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    public CategoryPriceRangeResponse(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
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
