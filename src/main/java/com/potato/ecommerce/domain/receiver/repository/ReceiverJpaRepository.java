package com.potato.ecommerce.domain.receiver.repository;

import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverJpaRepository extends JpaRepository<ReceiverEntity, Long> {

    List<ReceiverEntity> findAllByMember_Id(Long id);
}
