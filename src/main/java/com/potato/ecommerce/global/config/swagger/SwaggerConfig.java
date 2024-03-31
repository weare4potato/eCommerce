package com.potato.ecommerce.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("openAPI")
                .version("1.0")
                .description("swagger-ui 화면입니다"))
            .components(new Components()
                .addSecuritySchemes("bearer-key", new SecurityScheme()
                    .type(Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
            );
    }

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/api/v1/**"};
        String[] packagesToScan = {"com.potato.ecommerce"};
        return GroupedOpenApi.builder().group("springdoc-openapi")
            .pathsToMatch(paths)
            .packagesToScan(packagesToScan)
            .build();
    }

}
