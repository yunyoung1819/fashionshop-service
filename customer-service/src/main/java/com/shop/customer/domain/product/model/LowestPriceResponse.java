package com.shop.customer.domain.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LowestPriceResponse {
    private LowestBrandInfo lowestPrice;

    public LowestPriceResponse(BrandPriceInfo brandPriceInfo) {
        this.lowestPrice = new LowestBrandInfo(brandPriceInfo);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LowestBrandInfo {
        private String brand;
        private List<BrandPriceInfo.CategoryPrice> categories;
        private int totalAmount;

        public LowestBrandInfo(BrandPriceInfo brandPriceInfo) {
            this.brand = brandPriceInfo.getBrand();
            this.categories = brandPriceInfo.getCategories();
            this.totalAmount = brandPriceInfo.getTotalAmount();
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
}
