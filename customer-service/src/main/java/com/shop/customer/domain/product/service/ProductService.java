package com.shop.customer.domain.product.service;

import com.shop.customer.domain.category.repository.CategoryReadRepository;
import com.shop.customer.domain.product.model.response.CategoryLowestPriceResponse;
import com.shop.customer.domain.product.model.response.CategoryPriceRangeResponse;
import com.shop.customer.domain.product.model.response.LowestPriceResponse;
import com.shop.customer.domain.product.model.response.LowestPriceResponse.LowestBrandInfo;
import com.shop.customer.domain.product.repository.ProductReadRepository;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductReadRepository productReadRepository;
    private final CategoryReadRepository categoryReadRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "lowestPriceByCategory")
    public CategoryLowestPriceResponse getLowestPriceByCategory() {
        List<Product> products = productReadRepository.findLowestPricedProductsByCategory();

        Map<String, Product> lowestPriceProducts = products.stream()
            .collect(toMap(
                p -> p.getCategory().getName(),
                p -> p,
                (p1, p2) -> p1.getPrice() < p2.getPrice() ? p1 : p2
            ));

        List<CategoryLowestPriceResponse.CategoryPriceInfo> categoryPriceDetails = lowestPriceProducts.values()
            .stream()
            .map(p -> new CategoryLowestPriceResponse.CategoryPriceInfo(
                p.getCategory().getName(),
                p.getBrand().getName(),
                p.getPrice()
            )).toList();

        int totalAmount = categoryPriceDetails.stream()
            .mapToInt(CategoryLowestPriceResponse.CategoryPriceInfo::price)
            .sum();

        return new CategoryLowestPriceResponse(categoryPriceDetails, totalAmount);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "lowestPriceByBrand")
    public LowestPriceResponse getLowestPriceBrand() {
        List<Object[]> lowestTotalPriceBrands = productReadRepository.findLowestTotalPriceByBrand();

        if (lowestTotalPriceBrands.isEmpty()) {
            throw new RuntimeException("No brands found.");
        }

        String brandName = (String) lowestTotalPriceBrands.get(0)[0];
        int totalAmount = ((Number) lowestTotalPriceBrands.get(0)[1]).intValue();

        List<Product> products = productReadRepository.findByBrandName(brandName);

        List<LowestPriceResponse.LowestBrandInfo.CategoryPrice> categoryPrices = products.stream()
            .map(p -> new LowestPriceResponse.LowestBrandInfo.CategoryPrice(
                p.getCategory().getName(),
                p.getPrice()
            ))
            .toList();

        LowestBrandInfo info = new LowestBrandInfo(brandName, categoryPrices,
            totalAmount);

        return new LowestPriceResponse(info);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "priceRangeByCategory", key = "#categoryName")
    public CategoryPriceRangeResponse getPriceRangeByCategoryName(String categoryName) {

        Category category = categoryReadRepository.findByName(categoryName)
            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategoryName(
            categoryName);
        List<Product> highestPriceProducts = productReadRepository.findHighestPricedProductsByCategoryName(
            categoryName);

        List<CategoryPriceRangeResponse.PriceInfo> lowestPriceInfo = toPriceInfo(
            lowestPriceProducts);
        List<CategoryPriceRangeResponse.PriceInfo> highestPriceInfo = toPriceInfo(
            highestPriceProducts);

        return new CategoryPriceRangeResponse(category.getName(), lowestPriceInfo,
            highestPriceInfo);
    }

    private List<CategoryPriceRangeResponse.PriceInfo> toPriceInfo(
        List<Product> lowestPriceProducts) {
        return lowestPriceProducts.stream()
            .map(
                p -> new CategoryPriceRangeResponse.PriceInfo(p.getBrand().getName(), p.getPrice()))
            .toList();
    }
}
