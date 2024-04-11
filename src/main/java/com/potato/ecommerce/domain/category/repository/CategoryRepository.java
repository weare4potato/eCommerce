package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.entity.CategoryType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT DISTINCT c.oneDepth FROM CategoryEntity c")
    List<CategoryType> findDistinctOneDepth();

    @Query("SELECT DISTINCT c.twoDepth FROM CategoryEntity c")
    List<CategoryType> findDistinctTwoDepth();

    @Query("SELECT DISTINCT c.threeDepth FROM CategoryEntity c")
    List<CategoryType> findDistinctThreeDepth();

}
