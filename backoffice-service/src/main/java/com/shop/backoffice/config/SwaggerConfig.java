package com.shop.backoffice.config;

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
                        .title("Musinsa Shop Backoffice API")
                        .description("Musinsa Shop Backoffice API reference for developers")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi backofficeApi() {
        return GroupedOpenApi.builder()
                .group("backoffice")
                .pathsToMatch("/v1/backoffice/**")
                .build();
    }
}
