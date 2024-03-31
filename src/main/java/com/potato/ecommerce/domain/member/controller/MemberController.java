package com.potato.ecommerce.domain.member.controller;

import com.potato.ecommerce.domain.member.dto.CreateMember;
import com.potato.ecommerce.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        @RequestBody @Validated CreateMember dto
    ){
        memberService.createMember(dto);
        return ResponseEntity.status(204).build();
    }
}
