package com.shop.backoffice.integration;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.product.entity.Brand;
import com.shop.core.product.entity.Category;
import com.shop.core.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    private Brand savedBrand;
    private Category savedCategory;

    @BeforeEach
    public void setup() {
        // 기존 데이터 삭제
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        brandRepository.deleteAll();

        // 단일 브랜드/카테고리 생성
        savedBrand = brandRepository.save(new Brand("Nike"));
        savedCategory = categoryRepository.save(new Category("Shoes"));
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void testCreateProduct_Success() throws Exception {
        String payload = """
            {
              "brandId": %d,
              "categoryId": %d,
              "price": 1000
            }
            """.formatted(savedBrand.getId(), savedCategory.getId());

        mockMvc.perform(post("/v1/backoffice/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.brandId").value(savedBrand.getId()))
            .andExpect(jsonPath("$.data.categoryId").value(savedCategory.getId()))
            .andExpect(jsonPath("$.data.price").value(1000));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void testModifyProduct_Success() throws Exception {
        // 먼저 상품 저장
        Product product = productRepository.save(new Product(savedBrand, savedCategory, 1500));

        String payload = """
            {
              "brandId": %d,
              "categoryId": %d,
              "price": 2000
            }
            """.formatted(savedBrand.getId(), savedCategory.getId());

        mockMvc.perform(patch("/v1/backoffice/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.price").value(2000));
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    public void testCreateCategory_Success() throws Exception {
        mockMvc.perform(post("/v1/backoffice/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Accessories\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Accessories"));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    public void testModifyCategory_Success() throws Exception {
        mockMvc.perform(patch("/v1/backoffice/categories/{id}", savedCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Bags\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Bags"));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void testDeleteCategory_Success() throws Exception {
        mockMvc.perform(delete("/v1/backoffice/categories/{id}", savedCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("브랜드 등록 테스트")
    public void testCreateBrand_Success() throws Exception {
        mockMvc.perform(post("/v1/backoffice/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Adidas\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Adidas"));
    }

    @Test
    @DisplayName("브랜드 수정 테스트")
    public void testModifyBrand_Success() throws Exception {
        mockMvc.perform(patch("/v1/backoffice/brands/{id}", savedBrand.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Puma\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Puma"));
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    public void testDeleteBrand_Success() throws Exception {
        mockMvc.perform(delete("/v1/backoffice/brands/{id}", savedBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }
}
