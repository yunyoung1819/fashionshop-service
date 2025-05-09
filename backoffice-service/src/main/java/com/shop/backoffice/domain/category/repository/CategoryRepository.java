package com.shop.backoffice.domain.category.repository;

import com.shop.core.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
