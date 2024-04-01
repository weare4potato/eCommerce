package com.potato.ecommerce.domain.member.controller;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member API", description = "Member API 입니다.")
@RequestMapping("/api/v1/users")
@Slf4j
public class MemberController {

    /*
    TODO : 로그아웃, 비밀번호 수정, 회원 탈퇴
     */


    private final MemberService memberService;

    @PostMapping("/signup")
    @Tag(name = "Member API")
    @Operation(summary = "회원가입")
    public ResponseEntity<Void> signUp(
        @RequestBody @Validated SignUpDto dto
    ) {
        memberService.signUp(dto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/signin")
    @Tag(name = "Member API")
    @Operation(summary = "로그인")
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
    @Operation(summary = "정보 조회")
    public ResponseEntity<ResponseMember> getMember(
        HttpServletRequest request
    ) {
        String subject = getSubject(request);
        ResponseMember dto = memberService.getMember(subject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Tag(name = "Member API")
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<Void> passwordCheck(
        @RequestBody String password,
        HttpServletRequest request
    ) {
        memberService.passwordCheck(getSubject(request), password);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    @Tag(name = "Member API")
    @Operation(summary = "정보 수정")
    public ResponseEntity<ResponseMember> updateMember(
        @RequestBody @Validated UpdateMemberDto dto,
        HttpServletRequest request
    ) {
        return ResponseEntity.ok(
            memberService.updateMember(
                dto,
                getSubject(request))
        );
    }

    @PutMapping("/password")
    @Tag(name = "Member API")
    @Operation(summary = "비밀번호 수정")
    public ResponseEntity<String> updatePassword(
        @RequestBody @Validated UpdatePasswordDto dto,
        HttpServletRequest request
    ) {
        memberService.updatePassword(dto, getSubject(request));
        return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }
}
