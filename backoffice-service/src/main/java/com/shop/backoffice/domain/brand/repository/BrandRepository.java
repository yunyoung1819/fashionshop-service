package com.shop.backoffice.domain.brand.repository;

import com.shop.core.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
