package com.shop.backoffice.domain.brand.service;

import com.shop.backoffice.domain.brand.model.request.BrandRequest;
import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.core.entity.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.name())
                .build();

        brandRepository.save(brand);
    }

    @Transactional
    public void updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        Brand changedBrand = brand.toBuilder()
                .name(brandRequest.name())
                .build();

        brand.update(changedBrand);
    }


    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found: " + id));

        brandRepository.deleteById(id);
    }
}
