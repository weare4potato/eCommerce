package com.potato.ecommerce.global.jwt.interceptor;

import com.potato.ecommerce.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/v1/*")
            .excludePathPatterns(
                "/api/v1/signup",
                "/api/v1/signin",
                "/api/v1/shops/signup",
                "/api/v1/shops/signin"
                );

    }
}
