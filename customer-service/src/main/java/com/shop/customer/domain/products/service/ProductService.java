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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductReadRepository productReadRepository;

    private final CategoryReadRepository categoryReadRepository;

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return
     */
    public CategoryLowestPriceResponse getLowestPriceByCategory() {
        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategory();
        List<CategoryLowestPriceResponse.CategoryPriceInfo> categoryPriceInfos = lowestPriceProducts
                .stream()
                .map(prodouct -> new CategoryLowestPriceResponse.CategoryPriceInfo(
                        prodouct.getCategory().getName(),
                        prodouct.getBrand().getName(),
                        prodouct.getPrice()
                )).collect(Collectors.toList());

        int totalAmount = categoryPriceInfos.stream()
                .mapToInt(CategoryLowestPriceResponse.CategoryPriceInfo::getPrice)
                .sum();

        return new CategoryLowestPriceResponse(categoryPriceInfos, totalAmount);
    }

    public Map<String, Object> findLowestPricedCategory() {
        List<Product> products = productReadRepository.findLowestPricedProductsByCategory();
        List<Map<String, ? extends Object>> productDetails = products.stream()
                .map(p -> Map.of(
                        "category", p.getCategory(),
                        "brand", p.getBrand(),
                        "price", p.getPrice()
                )).collect(Collectors.toList());

        int total = products.stream().mapToInt(Product::getPrice).sum();

        return Map.of(
                "products", productDetails,
                "total", total
        );
    }


    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품 가격, 총액을 조회하는 API
     * @return
     */
    public LowestPriceResponse getLowestPriceByBrand() {
        List<Object[]> result = productReadRepository.findBrandWithLowestTotalPrice();
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


    /**
     * 특정 카테고리에서 최저가격 브랜드와 최고 가격 브랜드를 조회하는 API
     * @param categoryName
     * @return
     */
    public CategoryPriceRangeResponse getPriceRangeByCategory(String categoryName) {
        Category category = categoryReadRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));

        List<Product> lowestPriceProducts = productReadRepository.findLowestPricedProductsByCategoryName(categoryName);
        List<Product> highestPriceProducts = productReadRepository.findHighestPricedProductsByCategoryName(categoryName);

        List<CategoryPriceRangeResponse.PriceInfo> lowestPriceInfo = lowestPriceProducts.stream()
                .map(p -> new CategoryPriceRangeResponse.PriceInfo(
                        p.getBrand().getName(),
                        p.getPrice()
                )).toList();

        List<CategoryPriceRangeResponse.PriceInfo> highestPriceInfo = highestPriceProducts.stream()
                .map(p -> new CategoryPriceRangeResponse.PriceInfo(
                        p.getBrand().getName(),
                        p.getPrice()
                )).toList();

        return new CategoryPriceRangeResponse(category.getName(), lowestPriceInfo, highestPriceInfo);
    }
}
