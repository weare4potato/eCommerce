package com.potato.ecommerce.domain.member.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.CHANGE_PASSWORD_CHECK;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import com.potato.ecommerce.global.exception.custom.AuthenticationFailedException;
import com.potato.ecommerce.global.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseMember signUp(SignUpDto dto) {
        MemberEntity member = MemberEntity.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .userName(dto.getUsername())
            .phone(dto.getPhone())
            .role(UserRoleEnum.USER)
            .authStatus(false).build();

        return ResponseMember.fromEntity(memberJpaRepository.save(member));
    }

    @Transactional
    public String signIn(SignInDto dto) {
        MemberEntity member = findByEmail(dto.getEmail());

        if (member.isNotAuthCheck()) {
            throw new AuthenticationFailedException();
        }

        validateMemberPassword(member, dto.getPassword());

        return jwtUtil.createToken(member.getEmail(), member.getRole());
    }

    @Transactional
    public ResponseMember updatePassword(UpdatePasswordDto dto, String subject) {
        MemberEntity member = findByEmail(subject);

        validateMemberPassword(member, dto.getPassword());
        validateNewPassword(dto);

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        return ResponseMember.fromEntity(member);
    }

    @Transactional
    public ResponseMember updateMember(UpdateMemberDto dto, String subject) {
        MemberEntity member = findByEmail(subject);

        member.update(dto);

        return ResponseMember.fromEntity(member);
    }

    @Transactional
    public void deleteMember(String email) {
        MemberEntity member = findByEmail(email);

        memberJpaRepository.delete(member);
    }

    @Transactional
    public ResponseMember confirmMember(String email) {
        MemberEntity member = findByEmail(email);

        member.confirm();

        return ResponseMember.fromEntity(member);
    }


    public ResponseMember passwordCheck(String subject, String password) {
        MemberEntity member = findByEmail(subject);

        validateMemberPassword(member, password);

        return ResponseMember.fromEntity(member);
    }

    public ResponseMember getMember(String subject) {
        MemberEntity member = findByEmail(subject);

        return ResponseMember.fromEntity(member);
    }

    private MemberEntity findByEmail(String subject) {
        return memberJpaRepository.findByEmail(subject).orElseThrow(
            () -> new EntityNotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.toString()));
    }


    private void validateMemberPassword(MemberEntity member, String password) {
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
