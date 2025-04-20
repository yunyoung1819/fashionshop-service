package com.shop.core.product.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 상품 카테고리 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder(toBuilder = true)
    public Category(String name) {
        this.name = name;
    }

    /**
     * 카테고리명을 수정합니다.
     */
    public void updateName(String changedName) {
        this.name = changedName;
    }
}
