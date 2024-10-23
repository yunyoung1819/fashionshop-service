package com.shop.customer.domain.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BrandPriceInfo {
    private String brand;
    private List<CategoryPrice> categories;
    private int totalAmount;

    public BrandPriceInfo(String brand, List<CategoryPrice> categories, int totalAmount) {
        this.brand = brand;
        this.categories = categories;
        this.totalAmount = totalAmount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryPrice {
        private String category;
        private int price;

        public CategoryPrice(String category, int price) {
            this.category = category;
            this.price = price;
        }
    }
}
