package com.shop.customer.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Musinsa Shop Customer API")
                        .description("Musinsa Shop Customer Service API")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
                .group("customer")
                .pathsToMatch("/v1/customer/**")
                .build();
    }
}
