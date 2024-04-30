package com.potato.ecommerce.domain.s3.repository;

import com.potato.ecommerce.domain.s3.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
