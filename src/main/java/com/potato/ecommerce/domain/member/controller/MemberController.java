package com.potato.ecommerce.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.potato.ecommerce.domain.mail.service.MailService;
import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.ResponseSignInDto;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.service.MemberService;
import com.potato.ecommerce.domain.oauth.kakao.service.KakaoService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<ResponseSignInDto> signUp(@RequestBody @Validated SignUpDto dto) {
        ResponseMember member = memberService.signUp(dto);
        String message = mailService.sendMail(dto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseSignInDto.from(member, message));
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<Void> signIn(@RequestBody @Validated SignInDto dto) {
        String token = memberService.signIn(dto);

        return ResponseEntity.status(HttpStatus.OK)
            .header(JwtUtil.AUTHORIZATION_HEADER, token).build();
    }

    @GetMapping
    @Operation(summary = "단일 조회")
    public ResponseEntity<ResponseMember> getMember(HttpServletRequest request) {
        ResponseMember response = memberService.getMember(getSubject(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<ResponseMember> updatePassword(
        @RequestBody @Validated UpdatePasswordDto dto,
        HttpServletRequest request) {
        ResponseMember member = memberService.updatePassword(dto, getSubject(request));
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴")
    public ResponseEntity<Void> deleteMember(HttpServletRequest request) {
        memberService.deleteMember(getSubject(request));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping
    @Operation(summary = "비밀번호 확인")
    public ResponseEntity<ResponseMember> passwordCheck(@RequestBody String password,
        HttpServletRequest request) {
        ResponseMember member = memberService.passwordCheck(getSubject(request), password);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @GetMapping("/signup/confirm")
    public ResponseEntity<ResponseMember> updateAuth(@RequestParam("email") String email) {
        ResponseMember member = memberService.confirmMember(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Void> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        return ResponseEntity.status(HttpStatus.OK)
            .header(JwtUtil.AUTHORIZATION_HEADER, token).build();
    }

    private String getSubject(HttpServletRequest request) {
        return (String) request.getAttribute("subject");
    }

}
