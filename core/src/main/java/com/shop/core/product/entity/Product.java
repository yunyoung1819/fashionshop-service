package com.shop.core.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_brand_id", columnList = "brand_id"),
        @Index(name = "idx_product_category_id", columnList = "category_id"),
        @Index(name = "idx_product_price", columnList = "price")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int price;

    @Builder(toBuilder = true)
    public Product(Brand brand, Category category, int price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public void update(Brand brand, Category category, int price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }
}
