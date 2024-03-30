package com.potato.ecommerce.domain.receiver.repository;

import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<ReceiverEntity, Long> {

}
