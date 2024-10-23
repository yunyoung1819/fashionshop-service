package com.shop.core.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder(toBuilder = true)
    public Category(String name) {
        this.name = name;
    }

    public void update(Category changedCategory) {
        this.name = changedCategory.name;
    }
}
