package com.potato.ecommerce.global.jwt;

import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 30 * 60 * 1000L; // 30 분

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key;

    @PostConstruct

    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String tokenValue = request.getHeader(AUTHORIZATION_HEADER);
        log.info(tokenValue);
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(6);
        }
        return null;
    }

    // 토큰 뽑아오기
    public String substringToken(String tokenValue) {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.info("Not Found Token");
        throw new NullPointerException("Not found Token");
    }
    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            .getBody();
    }

    public String createToken(String email, UserRoleEnum role) {
        Date expireDate = createExpireDate(TOKEN_TIME);

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(email)
                .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                .setExpiration(expireDate)
                .setIssuedAt(new Date())
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createSellerToken(String businessNumber) {
        Date expireDate = createExpireDate(TOKEN_TIME);

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(businessNumber)
                .setExpiration(expireDate)
                .setIssuedAt(new Date())
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // Seller 토큰과 Member 토큰의 정책 차별 가능성
    private Date createExpireDate(long expireDate) {
        long curTime = (new Date()).getTime();
        return new Date(curTime + expireDate);
    }
//    public void addJwtToHeader(String token, HttpServletResponse response) {
//        response.addHeader(AUTHORIZATION_HEADER, token);
//    }

}
