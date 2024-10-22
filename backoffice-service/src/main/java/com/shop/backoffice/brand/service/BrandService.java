package com.shop.backoffice.brand.service;

import com.shop.backoffice.brand.dto.request.BrandRequest;
import com.shop.backoffice.brand.repository.BrandRepository;
import com.shop.core.entity.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public void addBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.getName())
                .build();

        brandRepository.save(brand);
    }

    @Transactional
    public void updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        Brand changeBrand = Brand.builder()
                .name(brand.getName())
                .build();
        brandRepository.save(changeBrand);
    }


    @Transactional
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
