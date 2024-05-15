package com.potato.ecommerce.domain.member.repository;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String subject);

    Optional<MemberEntity> findByKakaoId(Long kakaoId);
}
