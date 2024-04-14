package com.potato.ecommerce.global.config.swagger;

import static org.springframework.security.config.Elements.JWT;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
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
        Components components = new Components()
            .addSecuritySchemes(JWT, authScheme());
        SecurityRequirement securityItem = new SecurityRequirement()
            .addList(JWT);

        return new OpenAPI().info(new Info()
                .title("openAPI.")
                .version("1.0")
                .description("swagger-ui 화면입니다"))
            .components(components)
            .addSecurityItem(securityItem);
    }

    private SecurityScheme authScheme() {
        return new SecurityScheme().type(Type.APIKEY).scheme("Bearer").in(In.HEADER).name("Authorization");
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
