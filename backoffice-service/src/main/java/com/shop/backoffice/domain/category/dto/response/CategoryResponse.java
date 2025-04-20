package com.shop.backoffice.domain.category.dto.response;

import com.shop.core.product.entity.Category;
import java.util.Objects;

public record CategoryResponse(
    Long id,
    String name
) {
    public static CategoryResponse from(Category category) {
        Objects.requireNonNull(category, "Category must not be null");
        return new CategoryResponse(category.getId(), category.getName());
    }
}
