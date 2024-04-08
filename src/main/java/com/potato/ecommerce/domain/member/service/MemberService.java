package com.potato.ecommerce.domain.member.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.CHANGE_PASSWORD_CHECK;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import com.potato.ecommerce.global.exception.custom.AuthenticationFailedException;
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
            .authStatus(false)
            .build();

        memberRepository.save(member);
    }

    @Transactional
    public String signIn(SignInDto dto) {
        Member member = findBy(dto.getEmail());

        if (member.isNotAuthCheck()) {
            throw new AuthenticationFailedException();
        }

        validateMemberPassword(member, dto.getPassword());

        return jwtUtil.createToken(member.getEmail(), member.getRole());
    }

    @Transactional
    public void updatePassword(UpdatePasswordDto dto, String subject) {
        Member member = findBy(subject);

        validateMemberPassword(member, dto.getPassword());
        validateNewPassword(dto);

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        memberRepository.update(member);
    }

    @Transactional
    public ResponseMember updateMember(UpdateMemberDto dto, String subject) {
        Member member = findBy(subject);

        member.update(dto);

        memberRepository.update(member);
        return member.createResponseDTO();
    }

    @Transactional
    public void confirmMember(String email) {
        Member member = findBy(email);
        member.confirm();

        memberRepository.update(member);
    }


    public void passwordCheck(String subject, String password) {
        Member member = findBy(subject);

        validateMemberPassword(member, password);
    }

    public ResponseMember getMember(String subject) {
        Member member = findBy(subject);

        return member.createResponseDTO();
    }


    private Member findBy(String email) {
        return memberRepository.findMemberBy(email);
    }

    private void validateMemberPassword(Member member, String password) {
        if (member.isNotMatchPassword(passwordEncoder, password)) {
            throw new ValidationException(PASSWORD_NOT_MATCH.toString());
        }
    }

    private void validateNewPassword(UpdatePasswordDto dto) {
        if (dto.getPassword().equals(dto.getNewPassword())) {
            throw new ValidationException(CHANGE_PASSWORD_CHECK.toString());
        }
    }


}
