package com.potato.ecommerce.domain.member.controller;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member API", description = "Member API 입니다.")
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Tag(name = "Member API")
    @Operation(summary = "회원가입", description = "회원가입입니다.")
    public ResponseEntity<Void> signUp(
        @RequestBody @Validated SignUpDto dto
    ) {
        memberService.signUp(dto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/signin")
    @Tag(name = "Member API")
    @Operation(summary = "로그인", description = "로그인입니다.")
    public ResponseEntity<Void> signIn(
        @RequestBody @Validated SignInDto dto
    ) {
        String token = memberService.signIn(dto);
        ResponseCookie cookie = ResponseCookie
            .from("Authorization", token)
            .domain("localhost")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofMinutes(30L))
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }

    @GetMapping
    @Tag(name = "Member API")
    @Operation(summary = "정보 조회", description = "유저 조회입니다.")
    public ResponseEntity<ResponseMember> getMember(
        HttpServletRequest request
    ) {
        ResponseMember dto = memberService.getMember((String) request.getAttribute("subject"));
        return ResponseEntity.ok(dto);
    }
}
