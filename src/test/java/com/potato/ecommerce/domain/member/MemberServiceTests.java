package com.potato.ecommerce.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.potato.ecommerce.domain.member.dto.ResponseMember;
import com.potato.ecommerce.domain.member.dto.SignInDto;
import com.potato.ecommerce.domain.member.dto.SignUpDto;
import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import com.potato.ecommerce.domain.member.dto.UpdatePasswordDto;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.entity.UserRoleEnum;
import com.potato.ecommerce.domain.member.repository.MemberJpaRepository;
import com.potato.ecommerce.domain.member.service.MemberService;
import com.potato.ecommerce.global.jwt.JwtUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void Member_signup() {
        // Arrange
        final SignUpDto signUpDto = createMemberSignupRequest();
        MemberEntity member = createMember();

        given(memberJpaRepository.save(any())).willReturn(member);

        // Act
        final ResponseMember response = memberService.signUp(signUpDto);

        // Assert
        assertThat(response.getEmail()).isEqualTo(signUpDto.getEmail());
    }

    @Test
    void Member_signin() {
        // Arrange
        final SignInDto signInDto = createSignInDto();
        final MemberEntity member = createAuthMember();
        String token = "token";

        given(memberJpaRepository.findByEmail(signInDto.getEmail())).willReturn(
            Optional.of(member));
        given(jwtUtil.createToken(member.getEmail(), UserRoleEnum.USER)).willReturn(token);

        // Act
        final String response = memberService.signIn(signInDto);

        // Assert
        assertThat(response).isEqualTo(token);
    }

    @Test
    void Member_update() {
        // Arrange
        final UpdateMemberDto updateMemberDto = createUpdateMemberRequest();
        final MemberEntity member = createAuthMember();

        given(memberJpaRepository.findByEmail(member.getEmail())).willReturn(
            Optional.of(member));

        // Act
        final ResponseMember response = memberService.updateMember(updateMemberDto,
            member.getEmail());

        // Assert
        assertThat(response.getUsername()).isEqualTo(updateMemberDto.getUsername());
    }

    @Test
    void Member_update_password() {
        // Arrange
        String password = "123456789";
        String newPassword = "987654321";
        final UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(password, newPassword);
        final MemberEntity member = createAuthMember();
        String subject = "test@email.com";

        given(memberJpaRepository.findByEmail(subject)).willReturn(
            Optional.of(member));

        // Act
        final ResponseMember response = memberService.updatePassword(updatePasswordDto,
            subject);

        // Assert
        assertThat(passwordEncoder.matches(newPassword, member.getPassword())).isTrue();
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void Member_delete() {
        // Arrange
        String email = "test@email.com";
        final MemberEntity member = createAuthMember();

        given(memberJpaRepository.findByEmail(email)).willReturn(
            Optional.of(member));

        // Act + Assert
        memberService.deleteMember(email);

        // Assert
        verify(memberJpaRepository, times(1)).delete(member);
    }

    @Test
    void Member_email_auth() {
        // Arrange
        String email = "test@email.com";
        final MemberEntity member = createMember();

        given(memberJpaRepository.findByEmail(email)).willReturn(Optional.of(member));

        // Act
        memberService.confirmMember(email);

        // Assert
        assertThat(member.isAuthStatus()).isEqualTo(true);
    }

    @Test
    void Member_password_check() {
        // Arrange
        String email = "test@email.com";
        String password = "123456789";
        final MemberEntity member = createAuthMember();

        given(memberJpaRepository.findByEmail(email)).willReturn(Optional.of(member));

        // Act
        final ResponseMember response = memberService.passwordCheck(email, password);

        // Assert
        assertThat(response.getEmail()).isEqualTo(email);
    }

    @Test
    void Member_get() {
        // Arrange
        String email = "test@email.com";
        final MemberEntity member = createAuthMember();

        given(memberJpaRepository.findByEmail(email)).willReturn(Optional.of(member));

        // Act
        final ResponseMember response = memberService.getMember(email);

        // Assert
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
    }

    private static UpdateMemberDto createUpdateMemberRequest() {
        String username = "updateUsername";
        String phone = "updatePhone";
        return new UpdateMemberDto(username, phone);
    }

    private MemberEntity createMember() {
        return MemberEntity.builder()
            .email("test@email.com")
            .password(passwordEncoder.encode("123456789"))
            .userName("username")
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(false).build();
    }

    private static SignUpDto createMemberSignupRequest() {
        String email = "test@email.com";
        String username = "username";
        String password = "123456789";
        String phone = "01011112222";
        return new SignUpDto(email, username, password, phone);
    }

    private MemberEntity createAuthMember() {
        return MemberEntity.builder()
            .email("test@email.com")
            .password(passwordEncoder.encode("123456789"))
            .userName("username")
            .phone("01011112222")
            .role(UserRoleEnum.USER)
            .authStatus(true).build();
    }

    private SignInDto createSignInDto() {
        String email = "test@email.com";
        String password = "123456789";
        return new SignInDto(email, password);
    }
}
