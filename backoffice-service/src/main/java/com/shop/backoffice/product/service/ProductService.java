package com.shop.backoffice.product.service;

import com.shop.backoffice.brand.repository.BrandRepository;
import com.shop.backoffice.category.repository.CategoryRepository;
import com.shop.backoffice.product.dto.ProductRequest;
import com.shop.backoffice.product.repository.ProductRepository;
import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
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
    public void addProduct(ProductRequest productRequest) {

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest productRequest) {

            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            product.setBrand(brand);
            product.setCategory(category);
            product.setPrice(productRequest.getPrice());

            productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
