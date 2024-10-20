package com.shop.customer.domain.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CategoryLowestPriceResponse {
    private List<CategoryPriceInfo> categories;
    private int totalAmount;
    private String error;
    private String errorMessage;

    public CategoryLowestPriceResponse(List<CategoryPriceInfo> categories, int totalAmount) {
        this.categories = categories;
        this.totalAmount = totalAmount;
    }

    public CategoryLowestPriceResponse(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    @Getter
    @Setter
    public static class CategoryPriceInfo {
        private String category;
        private String brand;
        private int price;

        public CategoryPriceInfo(String category, String brand, int price) {
            this.category = category;
            this.brand = brand;
            this.price = price;
        }
    }
}
