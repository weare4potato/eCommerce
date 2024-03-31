package com.potato.ecommerce.global.jwt.filter;

import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = jwtUtil.getTokenFromRequest(httpServletRequest);
        String subject = jwtUtil.getUserInfoFromToken(token).getSubject();

        log.info("필터 진입");
        log.info(subject);
        request.setAttribute("subject", subject);
        chain.doFilter(request, response);
    }
}
