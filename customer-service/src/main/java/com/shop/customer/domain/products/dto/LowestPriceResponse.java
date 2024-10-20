package com.shop.customer.domain.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LowestPriceResponse {
    private LowestBrandInfo lowestPrice;

    public LowestPriceResponse(BrandPriceInfo brandPriceInfo) {
        this.lowestPrice = new LowestBrandInfo(brandPriceInfo);
    }

    public LowestPriceResponse(String error, String details) {
        this.lowestPrice = new LowestBrandInfo(error, details);
    }

    @Getter
    @Setter
    public static class LowestBrandInfo {
        private String brand;
        private List<BrandPriceInfo.CategoryPrice> categories;
        private String totalAmount;
        private String error;
        private String details;

        public LowestBrandInfo(BrandPriceInfo brandPriceInfo) {
            this.brand = brandPriceInfo.getBrand();
            this.categories = brandPriceInfo.getCategories();
            this.totalAmount = String.format("%,d", brandPriceInfo.getTotalAmount());
        }

        public LowestBrandInfo(String error, String details) {
            this.error = error;
            this.details = details;
        }

        @Getter
        @Setter
        public static class CategoryPrice {
            private String category;
            private String price;

            public CategoryPrice(String category, int price) {
                this.category = category;
                this.price = String.format("%,d", price);
            }
        }
    }



}
