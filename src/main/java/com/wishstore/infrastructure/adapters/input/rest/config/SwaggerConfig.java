package com.wishstore.infrastructure.adapters.input.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wishstoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WishStore API")
                        .description("API REST del sistema de lista de deseos WishStore")
                        .version("v1.0"));
    }
}