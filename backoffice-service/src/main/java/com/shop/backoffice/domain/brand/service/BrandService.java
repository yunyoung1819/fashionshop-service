package com.shop.backoffice.domain.brand.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.core.product.entity.Brand;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public Brand createBrand(String name) {
        Brand brand = Brand.builder()
            .name(name)
            .build();
        return brandRepository.save(brand);
    }

    @Transactional
    public Brand updateBrandName(Long id, String newName) {
        Brand changedBrand = brandRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + id));
        changedBrand.updateName(newName);
        return changedBrand;
    }

    @Transactional
    public void deleteBrandById(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new EntityNotFoundException("Brand not found: " + id);
        }
        brandRepository.deleteById(id);
    }
}
