package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.OneDepthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneDepthRepository extends JpaRepository<OneDepthEntity, Long> {

}
