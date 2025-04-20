package com.shop.backoffice.domain.brand.dto.response;

import com.shop.core.product.entity.Brand;
import java.util.Objects;

public record BrandResponse(
    Long id,
    String name
) {
    public static BrandResponse from(Brand entity) {
        Objects.requireNonNull(entity, "Brand must not be null");
        return new BrandResponse(entity.getId(), entity.getName());
    }
}
