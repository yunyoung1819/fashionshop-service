package com.shop.backoffice.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private Long brandId;
    private Long categoryId;
    private int price;
}
