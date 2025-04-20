package com.shop.core.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 브랜드 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder(toBuilder = true)
    public Brand(String name) {
        this.name = name;
    }

    /**
     * 브랜드명을 수정합니다.
     */
    public void updateName(String name) {
        this.name = name;
    }
}
