package com.potato.ecommerce.domain.member.service;

import com.potato.ecommerce.domain.member.dto.CreateMember;
import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member createMember(CreateMember dto) {
        Member member = Member.builder()
            .email(dto.getEmail())
            .password(dto.getPassword())
            .userName(dto.getUsername())
            .phone(dto.getPhone())
            .build();

        memberRepository.save(MemberEntity.fromModel(member));
        return member;
    }
}
