package com.potato.ecommerce.global.jwt.interceptor;

import com.potato.ecommerce.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns(
                "/api/v1/users/signup",
                "/api/v1/users/signin",
                "/api/v1/shops/signup",
                "/api/v1/shops/signin",
                "/api/v1/revenue",
                "/api/v1/users/signup/confirm",
                "/api/v1/products/all",
                "/api/v1/products/details/*",
                "/api/v1/products/{productId}/shops",
                "/api/v1/shops/*/shop-products",
                "/api/v1/categories/**",
                "/api/v1/products/categories",
                "/api/v1/manage/**"
            );


    }
}
