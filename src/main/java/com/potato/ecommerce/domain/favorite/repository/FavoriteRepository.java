package com.potato.ecommerce.domain.favorite.repository;

import com.potato.ecommerce.domain.favorite.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

}
