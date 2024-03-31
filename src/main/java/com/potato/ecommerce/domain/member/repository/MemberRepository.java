package com.potato.ecommerce.domain.member.repository;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByEmail(String email);
}
