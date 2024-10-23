package com.shop.backoffice.unit.domain.brand.service;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.brand.service.BrandService;
import com.shop.backoffice.domain.brand.model.request.BrandRequest;
import com.shop.core.entity.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 생성 테스트")
    void createBrandSuccess() {
        BrandRequest request = new BrandRequest("Adidas");

        brandService.createBrand(request);

        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 수정 테스트 - 브랜드 없음")
    public void modifyBrandFailBrandNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        BrandRequest request = new BrandRequest("Adidas");

        assertThrows(IllegalArgumentException.class, () -> brandService.updateBrand(1L, request));
    }

    @Test
    @DisplayName("브랜드 삭제 테스트 - 브랜드 없음")
    public void deleteBrandFail() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> brandService.deleteBrand(1L));
    }
}
