package com.potato.ecommerce.domain.member.repository;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findMemberBy(String email) {
        return Member.fromEntity(memberJpaRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.toString())
        ));
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member.toEntity());
    }

    @Override
    public void update(Member member) {
        memberJpaRepository.save(member.toEntity());
    }
}
