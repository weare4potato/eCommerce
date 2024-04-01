package com.potato.ecommerce.domain.member.service;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignUpDto dto) {
        Member member = Member.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .userName(dto.getUsername())
            .phone(dto.getPhone())
            .role(UserRoleEnum.USER)
            .build();

        memberRepository.save(member);
    }

    @Transactional
    public String signIn(SignInDto dto) {
        Member member = findBy(dto.getEmail());

        validatePassword(member, dto.getPassword());

        return jwtUtil.createToken(member.getEmail(), member.getRole());
    }

    public ResponseMember getMember(String subject) {
        Member member = findBy(subject);

        return member.createResponseDTO();
    }

    @Transactional
    public ResponseMember updateMember(UpdateMemberDto dto, String subject) {
        Member member = findBy(subject);

        member.update(dto);

        memberRepository.update(member);
        return member.createResponseDTO();
    }

    @Transactional
    public void passwordCheck(String subject, String password) {
        Member member = findBy(subject);
        validatePassword(member, password);
    }

    private Member findBy(String email) {
        return memberRepository.findMemberBy(email);
    }

    private void validatePassword(Member member, String password) {
        if (member.isNotMatchPassword(passwordEncoder, password)) {
            throw new ValidationException("패스워드가 일치하지 않습니다.");
        }
    }


}
