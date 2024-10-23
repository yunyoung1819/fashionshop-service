package com.shop.customer.domain.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/customer/products")
public class ProductViewController {

    /**
     * 상품 API를 보여주는 화면
     * @return 상품 API 화면
     */
    @GetMapping("/view")
    public String productViewPage() {
        return "products";
    }
}
