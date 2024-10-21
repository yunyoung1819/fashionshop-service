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

import java.util.List;
import java.util.Map;

import static com.shop.customer.domain.products.dto.CategoryLowestPriceResponse.*;
import static com.shop.customer.domain.products.dto.CategoryPriceRangeResponse.*;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductReadRepository productReadRepository;

    private final CategoryReadRepository categoryReadRepository;


    @Transactional(readOnly = true)
    public CategoryLowestPriceResponse getLowestPriceByCategory() {
        List<Product> products = productReadRepository.findLowestPricedProductsByCategory();

        Map<String, Product> lowestPriceProducts = products.stream()
                .collect(toMap(
                        p -> p.getCategory().getName(),
                        p -> p,
                        (p1, p2) -> p1.getPrice() < p2.getPrice() ? p1 : p2
                ));

        List<CategoryPriceInfo> categoryPriceInfo = lowestPriceProducts.values()
                .stream()
                .map(p -> new CategoryPriceInfo(
                        p.getCategory().getName(),
                        p.getBrand().getName(),
                        p.getPrice()
                )).toList();

        int totalAmount = categoryPriceInfo.stream()
                .mapToInt(CategoryPriceInfo::getPrice)
                .sum();

        return new CategoryLowestPriceResponse(categoryPriceInfo, totalAmount);
    }


    @Transactional(readOnly = true)
    public LowestPriceResponse getLowestPriceBrand() {
        List<Object[]> lowestTotalPriceBrands = productReadRepository.findLowestTotalPriceByBrand();

        if (lowestTotalPriceBrands.isEmpty()) {
            throw new RuntimeException("No brands found.");
        }

        String brandName = (String) lowestTotalPriceBrands.get(0)[0];
        int totalAmount = ((Number) lowestTotalPriceBrands.get(0)[1]).intValue();

        List<Product> products = productReadRepository.findByBrandName(brandName);

        List<BrandPriceInfo.CategoryPrice> categoryPrices = products.stream()
                .map(p -> new BrandPriceInfo.CategoryPrice(
                        p.getCategory().getName(),
                        p.getPrice()
                )).toList();

        BrandPriceInfo brandPriceInfo = new BrandPriceInfo(brandName, categoryPrices, totalAmount);

        return new LowestPriceResponse(brandPriceInfo);
    }

    @Transactional(readOnly = true)
    public CategoryPriceRangeResponse getPriceRangeByCategoryName(String categoryName) {

        Category category = categoryReadRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategoryName(categoryName);
        List<Product> highestPriceProducts = productReadRepository.findHighestPricedProductsByCategoryName(categoryName);

        List<PriceInfo> lowestPriceInfo = toPriceInfo(lowestPriceProducts);
        List<PriceInfo> highestPriceInfo = toPriceInfo(highestPriceProducts);

        return new CategoryPriceRangeResponse(category.getName(), lowestPriceInfo, highestPriceInfo);
    }

    private List<PriceInfo> toPriceInfo(List<Product> lowestPriceProducts) {
        return lowestPriceProducts.stream()
                .map(p -> new PriceInfo(p.getBrand().getName(), p.getPrice()))
                .toList();
    }
}
