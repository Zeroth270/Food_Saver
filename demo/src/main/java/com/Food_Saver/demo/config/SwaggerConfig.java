package com.Food_Saver.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI foodSaverOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Saver API")
                        .description("API documentation for Food Saver Application")
                        .version("1.0.0"));
    }
}
