package com.shop.customer.domain.products.repository;

import com.shop.core.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReadRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category = p.category)")
    List<Product> findLowestPricedProductsByCategory();

    @Query("SELECT p.brand.name, SUM(p.price) FROM Product p GROUP BY p.brand ORDER BY SUM(p.price) ASC")
    List<Object[]> findLowestTotalPriceByBrand();

    @Query("SELECT p FROM Product p WHERE p.brand.name = :brandName")
    List<Product> findByBrandName(@Param("brandName") String brandName);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category = p.category)")
    List<Product> findLowestPricedProductsByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price = (SELECT MAX(p2.price) FROM Product p2 WHERE p2.category = p.category)")
    List<Product> findHighestPricedProductsByCategoryName(String categoryName);
}
