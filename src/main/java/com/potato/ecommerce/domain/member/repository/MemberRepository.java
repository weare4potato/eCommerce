package com.potato.ecommerce.domain.member.repository;

import com.potato.ecommerce.domain.member.model.Member;

public interface MemberRepository {
    Member findMemberBy(String email);
    void save(Member member);
    void update(Member member);
}
