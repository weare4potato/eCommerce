package com.potato.ecommerce.global.jwt.interceptor;

import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String token = jwtUtil.getTokenFromHeader(request);

        if(ObjectUtils.isEmpty(token)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        String subject = jwtUtil.getUserInfoFromToken(token).getSubject();

        request.setAttribute("subject", subject);
        return true;

    }

}
