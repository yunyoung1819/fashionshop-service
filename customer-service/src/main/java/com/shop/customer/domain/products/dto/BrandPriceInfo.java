package com.shop.customer.domain.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    public static class CategoryPrice {
        private String category;
        private int price;

        public CategoryPrice(String category, int price) {
            this.category = category;
            this.price = price;
        }
    }
}
