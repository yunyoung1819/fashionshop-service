package com.shop.backoffice.domain.product.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.model.request.ProductRequest;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.product.entity.Brand;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
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
    public void createProduct(ProductRequest productRequest) {

        Brand brand = findBrand(productRequest);

        Category category = findCategory(productRequest);

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .price(productRequest.price())
                .build();

        productRepository.save(product);
    }

    private Category findCategory(ProductRequest productRequest) {
        Long categoryId = productRequest.categoryId();
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
    }

    private Brand findBrand(ProductRequest productRequest) {
        Long brandId = productRequest.brandId();
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found: " + brandId));
    }

    @Transactional
    public void modifyProduct(Long id, ProductRequest productRequest) {
        Brand brand = findBrand(productRequest);
        Category category = findCategory(productRequest);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.update(brand, category, productRequest.price());

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        productRepository.deleteById(id);
    }
}
