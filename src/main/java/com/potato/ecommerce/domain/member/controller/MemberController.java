package com.potato.ecommerce.domain.member.controller;

import com.potato.ecommerce.domain.mail.service.MailService;
import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.service.MemberService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Member API")
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@RequestBody @Validated SignUpDto dto) {
        memberService.signUp(dto);
        String message = mailService.sendMail(dto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<Void> signIn(@RequestBody @Validated SignInDto dto) {
        String token = memberService.signIn(dto);

        ResponseCookie cookie = ResponseCookie
            .from(JwtUtil.AUTHORIZATION_HEADER, token)
            .domain("localhost")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofMinutes(30L))
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping
    @Operation(summary = "단일 조회")
    public ResponseEntity<ResponseMember> getMember(HttpServletRequest request) {
        ResponseMember response = memberService.getMember(getSubject(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<Long> passwordCheck(@RequestBody String password,
        HttpServletRequest request) {
        Long id = memberService.passwordCheck(getSubject(request), password);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PutMapping
    @Operation(summary = "정보 수정")
    public ResponseEntity<ResponseMember> updateMember(@RequestBody @Validated UpdateMemberDto dto,
        HttpServletRequest request) {
        ResponseMember response = memberService.updateMember(dto, getSubject(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 수정")
    public ResponseEntity<Long> updatePassword(@RequestBody @Validated UpdatePasswordDto dto,
        HttpServletRequest request) {
        Long id = memberService.updatePassword(dto, getSubject(request));
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/signup/confirm")
    public ResponseEntity<Long> updateAuth(@RequestParam("email") String email) {
        Long id = memberService.confirmMember(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }

}
