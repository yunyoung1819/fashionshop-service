package com.shop.customer.domain.products.service;

import com.shop.customer.domain.category.repository.CategoryReadRepository;
import com.shop.customer.domain.products.dto.BrandPriceInfo;
import com.shop.customer.domain.products.dto.CategoryLowestPriceResponse;
import com.shop.customer.domain.products.dto.CategoryPriceRangeResponse;
import com.shop.customer.domain.products.dto.LowestPriceResponse;
import com.shop.customer.domain.products.repository.ProductReadRepository;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shop.customer.domain.products.dto.CategoryLowestPriceResponse.*;
import static com.shop.customer.domain.products.dto.CategoryPriceRangeResponse.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductReadRepository productReadRepository;

    private final CategoryReadRepository categoryReadRepository;

    @Transactional(readOnly = true)
    public CategoryLowestPriceResponse getLowestPriceByCategory() {
        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategory();

        Map<String, Product> uniqueLowestPriceProducts = lowestPriceProducts.stream()
                .collect(Collectors.toMap(p -> p.getCategory().getName(), p -> p, (p1, p2) -> p1.getPrice() < p2.getPrice() ? p1 : p2));

        List<CategoryPriceInfo> categoryPriceInfos = uniqueLowestPriceProducts.values().stream()
                .map(p -> new CategoryPriceInfo(
                        p.getCategory().getName(),
                        p.getBrand().getName(),
                        p.getPrice()
                )).collect(Collectors.toList());

        int totalAmount = categoryPriceInfos.stream()
                .mapToInt(CategoryPriceInfo::getPrice)
                .sum();

        return new CategoryLowestPriceResponse(categoryPriceInfos, totalAmount);
    }

    @Transactional(readOnly = true)
    public LowestPriceResponse getLowestPriceByBrand() {
        List<Object[]> result = productReadRepository.findLowestTotalPriceByBrand();
        if (result.isEmpty()) {
            throw new RuntimeException("No brands found.");
        }

        String lowestBrand = (String) result.get(0)[0];
        int totalAmount = ((Number) result.get(0)[1]).intValue();

        List<Product> products = productReadRepository.findByBrandName(lowestBrand);
        List<BrandPriceInfo.CategoryPrice> categoryPrices = products.stream()
                .map(p -> new BrandPriceInfo.CategoryPrice(p.getCategory().getName(), p.getPrice()))
                .collect(Collectors.toList());

        BrandPriceInfo brandPriceInfo = new BrandPriceInfo(lowestBrand, categoryPrices, totalAmount);

        return new LowestPriceResponse(brandPriceInfo);
    }

    @Transactional(readOnly = true)
    public CategoryPriceRangeResponse getPriceRangeByCategory(String categoryName) {
        Category category = categoryReadRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));

        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategoryName(categoryName);
        List<Product> highestPriceProducts = productReadRepository.findHighestPricedProductsByCategoryName(categoryName);

        List<PriceInfo> lowestPriceInfo = lowestPriceProducts.stream()
                .map(p -> new PriceInfo(
                        p.getBrand().getName(),
                        p.getPrice()
                )).toList();

        List<PriceInfo> highestPriceInfo = highestPriceProducts.stream()
                .map(p -> new PriceInfo(
                        p.getBrand().getName(),
                        p.getPrice()
                )).toList();

        return new CategoryPriceRangeResponse(category.getName(), lowestPriceInfo, highestPriceInfo);
    }
}
