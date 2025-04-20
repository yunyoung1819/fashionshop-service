package com.shop.backoffice.domain.product.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.product.entity.Brand;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product createProduct(Long brandId, Long categoryId, int price) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + brandId));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

        Product p = Product.builder()
            .brand(brand)
            .category(category)
            .price(price)
            .build();
        return productRepository.save(p);
    }

    @Transactional
    public Product updateProduct(Long id, Long brandId, Long categoryId, int price) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + brandId));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

        Product existing = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));

        existing.update(brand, category, price);
        return existing;
    }

    @Transactional
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}
