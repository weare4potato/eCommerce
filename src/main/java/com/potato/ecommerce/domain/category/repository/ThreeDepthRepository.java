package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.ThreeDepthEntity;
import com.potato.ecommerce.domain.category.entity.TwoDepthEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreeDepthRepository extends JpaRepository<ThreeDepthEntity, Long> {
    List<ThreeDepthEntity> findByTwoDepth(TwoDepthEntity twoDepth);

}
