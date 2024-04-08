package com.potato.ecommerce.domain.member.controller;

import com.potato.ecommerce.domain.mail.service.MailService;
import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.message.MemberMessage;
import com.potato.ecommerce.domain.member.service.MemberService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member API", description = "Member API 입니다.")
@RequestMapping("/api/v1/users")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(
        @RequestBody @Validated SignUpDto dto
    ) {
        memberService.signUp(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mailService.sendMail(dto.getEmail()));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<Void> signIn(
        @RequestBody @Validated SignInDto dto
    ) {
        String token = memberService.signIn(dto);
        ResponseCookie cookie = ResponseCookie
            .from(JwtUtil.AUTHORIZATION_HEADER, token)
            .domain("localhost")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofMinutes(30L))
            .build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }

    @GetMapping
    @Operation(summary = "정보 조회")
    public ResponseEntity<ResponseMember> getMember(
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getMember(getSubject(request)));
    }

    @PostMapping
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<Void> passwordCheck(
        @RequestBody String password,
        HttpServletRequest request
    ) {
        memberService.passwordCheck(getSubject(request), password);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    @PutMapping
    @Operation(summary = "정보 수정")
    public ResponseEntity<ResponseMember> updateMember(
        @RequestBody @Validated UpdateMemberDto dto,
        HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.updateMember(dto, getSubject(request)));
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 수정")
    public ResponseEntity<String> updatePassword(
        @RequestBody @Validated UpdatePasswordDto dto,
        HttpServletRequest request
    ) {
        memberService.updatePassword(dto, getSubject(request));
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(MemberMessage.UPDATE_PASSWORD);
    }

    @GetMapping("/signup/confirm")
    public ResponseEntity<String> updateAuth(
        @RequestParam("email") String email
    ) {
        memberService.confirmMember(email);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MemberMessage.CONFIRM_AUTH);
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }

}
