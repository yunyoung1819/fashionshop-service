package com.shop.core.product.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 브랜드·카테고리·가격 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_brand_id", columnList = "brand_id"),
        @Index(name = "idx_product_category_id", columnList = "category_id"),
        @Index(name = "idx_product_price", columnList = "price")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "price"})
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Builder(toBuilder = true)
    public Product(Brand brand, Category category, int price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    /**
     * 브랜드, 카테고리, 가격을 수정합니다.
     * @param brand
     * @param category
     * @param price
     */
    public void update(Brand brand, Category category, int price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }
}
