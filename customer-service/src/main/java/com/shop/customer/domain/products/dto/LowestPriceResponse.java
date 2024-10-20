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


    @Getter
    @Setter
    public static class LowestBrandInfo {
        private String brand;
        private List<BrandPriceInfo.CategoryPrice> categories;
        private String totalAmount;

        public LowestBrandInfo(BrandPriceInfo brandPriceInfo) {
            this.brand = brandPriceInfo.getBrand();
            this.categories = brandPriceInfo.getCategories();
            this.totalAmount = String.format("%,d", brandPriceInfo.getTotalAmount());
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
