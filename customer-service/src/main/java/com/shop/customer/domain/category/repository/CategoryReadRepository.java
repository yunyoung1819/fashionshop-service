package com.shop.customer.domain.category.repository;

import com.shop.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryReadRepository extends JpaRepository<Category, Long>  {

    Optional<Category> findByName(String categoryName);

}
