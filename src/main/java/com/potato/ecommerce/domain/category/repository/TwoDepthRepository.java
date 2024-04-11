package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.OneDepthEntity;
import com.potato.ecommerce.domain.category.entity.TwoDepthEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoDepthRepository extends JpaRepository<TwoDepthEntity, Long> {

    List<TwoDepthEntity> findByOneDepth(OneDepthEntity oneDepth);

}
