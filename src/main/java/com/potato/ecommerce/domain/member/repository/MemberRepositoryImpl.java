package com.potato.ecommerce.domain.member.repository;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.model.Member;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findMemberBy(String email) {
        return memberJpaRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("해당 유저가 존재하지 않습니다.")
        ).toModel();
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(MemberEntity.fromModel(member));
    }

    @Override
    public void update(Member member) {
        memberJpaRepository.save(MemberEntity.fromModel(member));
    }
}
