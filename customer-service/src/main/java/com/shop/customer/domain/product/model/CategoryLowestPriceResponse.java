package com.shop.customer.domain.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryLowestPriceResponse {
    private List<CategoryPriceInfo> categories;
    private int totalAmount;

    public CategoryLowestPriceResponse(List<CategoryPriceInfo> categories, int totalAmount) {
        this.categories = categories;
        this.totalAmount = totalAmount;
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
