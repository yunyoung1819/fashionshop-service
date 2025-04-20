package com.shop.backoffice.domain.product.dto.request;

import com.shop.core.product.entity.Product;
import java.util.Objects;

public record ProductResponse(
    Long id,
    Long brandId,
    Long categoryId,
    int price
) {
    public static ProductResponse from(Product entity) {
        Objects.requireNonNull(entity, "Product must not be null");
        return new ProductResponse(
            entity.getId(),
            entity.getBrand().getId(),
            entity.getCategory().getId(),
            entity.getPrice()
        );
    }
}