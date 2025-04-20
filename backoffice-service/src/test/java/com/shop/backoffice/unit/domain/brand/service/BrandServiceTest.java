package com.shop.backoffice.unit.domain.brand.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.brand.service.BrandService;
import com.shop.core.product.entity.Brand;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 생성 성공")
    void createBrandSuccess() {
        // given
        String name = "Adidas";
        Brand saved = Brand.builder().name(name).build();
        when(brandRepository.save(any())).thenReturn(saved);

        // when
        Brand result = brandService.createBrand(name);

        // then
        assertNotNull(result);
        assertEquals(name, result.getName());
        ArgumentCaptor<Brand> captor = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository).save(captor.capture());
        assertEquals(name, captor.getValue().getName());
    }

    @Test
    @DisplayName("브랜드 수정 성공")
    void updateBrandSuccess() {
        Brand existing = Brand.builder().name("OldName").build();
        when(brandRepository.findById(1L)).thenReturn(Optional.of(existing));

        Brand updated = brandService.updateBrandName(1L, "NewName");
        assertEquals("NewName", updated.getName());
        verify(brandRepository, never()).save(any());
    }

    @Test
    @DisplayName("브랜드 삭제 실패 - 존재하지 않는 ID")
    void deleteBrandFailNotFound() {
        when(brandRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () ->
            brandService.deleteBrandById(1L)
        );
    }

    @Test
    @DisplayName("브랜드 삭제 성공")
    void deleteBrandSuccess() {
        when(brandRepository.existsById(1L)).thenReturn(true);
        brandService.deleteBrandById(1L);
        verify(brandRepository).deleteById(1L);
    }
}
