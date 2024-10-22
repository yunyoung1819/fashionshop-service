package com.shop.backoffice.product.service;

import com.shop.backoffice.brand.repository.BrandRepository;
import com.shop.backoffice.category.repository.CategoryRepository;
import com.shop.backoffice.product.dto.request.ProductRequest;
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
    public void createProduct(ProductRequest productRequest) {

        Brand brand = findBrand(productRequest);

        Category category = findCategory(productRequest);

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
    }

    private Category findCategory(ProductRequest productRequest) {
        Long categoryId = productRequest.getCategoryId();
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
    }

    private Brand findBrand(ProductRequest productRequest) {
        Long brandId = productRequest.getBrandId();
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found: " + brandId));
    }

    @Transactional
    public void modifyProduct(Long id, ProductRequest productRequest) {

        Brand brand = findBrand(productRequest);

        Category category = findCategory(productRequest);

        productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product changeProduct = Product.builder()
                .brand(brand)
                .category(category)
                .price(productRequest.getPrice())
                .build();

        productRepository.save(changeProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        productRepository.deleteById(id);
    }
}
