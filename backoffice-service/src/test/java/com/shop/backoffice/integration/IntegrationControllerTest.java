package com.shop.backoffice.integration;

import com.shop.backoffice.domain.brand.repository.BrandRepository;
import com.shop.backoffice.domain.category.repository.CategoryRepository;
import com.shop.backoffice.domain.product.repository.ProductRepository;
import com.shop.core.entity.Brand;
import com.shop.core.entity.Category;
import com.shop.core.entity.Product;
import jakarta.persistence.EntityManager;
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
//        Brand brand = new Brand("Nike");
//        brandRepository.save(brand);
//
//        Category category = new Category("Shoes");
//        categoryRepository.save(category);

        savedBrand = brandRepository.save(new Brand("Nike"));
        savedCategory = categoryRepository.save(new Category("Shoes"));
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void testCreateProduct_Success() throws Exception {
        mockMvc.perform(post("/v1/backoffice/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brandId\": 1, \"categoryId\": 1, \"price\": 1000 }"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품 등록 실패 테스트 - 브랜드 없음")
    public void testCreateProduct_Failure_InvalidBrand() throws Exception {
        mockMvc.perform(post("/v1/backoffice/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brandId\": 99, \"categoryId\": 1, \"price\": 1000 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void testModifyProduct_Success() throws Exception {
        Product product = productRepository.save(new Product(savedBrand, savedCategory, 1500));
        mockMvc.perform(patch("/v1/backoffice/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brandId\": " + savedBrand.getId() + ", \"categoryId\": " + savedCategory.getId() + ", \"price\": 2000 }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 수정 실패 테스트 - 상품 없음")
    public void testModifyProduct_Failure_ProductNotFound() throws Exception {
        mockMvc.perform(patch("/v1/backoffice/product/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brandId\": 1, \"categoryId\": 1, \"price\": 2000 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void testDeleteProduct_Success() throws Exception {
        Product product = productRepository.save(new Product(savedBrand, savedCategory, 1500));
        mockMvc.perform(delete("/v1/backoffice/product/" + product.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제 실패 테스트 - 상품 없음")
    public void testDeleteProduct_Failure_ProductNotFound() throws Exception {
        mockMvc.perform(delete("/v1/backoffice/product/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    public void testCreateCategory_Success() throws Exception {
        mockMvc.perform(post("/v1/backoffice/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Accessories\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    public void testModifyCategory_Success() throws Exception {
        Category category = categoryRepository.findById(1L).get();

        mockMvc.perform(patch("/v1/backoffice/category/" + category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bags\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void testDeleteCategory_Success() throws Exception {
        Category category = categoryRepository.findById(1L).get();

        productRepository.deleteAll();

        mockMvc.perform(delete("/v1/backoffice/category/" + category.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("브랜드 등록 테스트")
    public void testCreateBrand_Success() throws Exception {
        mockMvc.perform(post("/v1/backoffice/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Adidas\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("브랜드 수정 테스트")
    public void testModifyBrand_Success() throws Exception {
        Brand brand = brandRepository.findById(1L).get();

        mockMvc.perform(patch("/v1/backoffice/brand/" + brand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Puma\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    public void testDeleteBrand_Success() throws Exception {
        Brand brand = brandRepository.findById(1L).get();

        productRepository.deleteAll();

        mockMvc.perform(delete("/v1/backoffice/brand/" + brand.getId()))
                .andExpect(status().isOk());
    }
}
