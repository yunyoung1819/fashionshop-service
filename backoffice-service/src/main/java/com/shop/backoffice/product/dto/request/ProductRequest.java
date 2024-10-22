package com.shop.backoffice.product.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;;

@Getter
@RequiredArgsConstructor
public class ProductRequest {
    private final Long brandId;
    private final Long categoryId;
    private final int price;
}
