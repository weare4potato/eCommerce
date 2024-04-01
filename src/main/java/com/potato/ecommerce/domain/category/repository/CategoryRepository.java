package com.potato.ecommerce.domain.category.repository;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.entity.CategoryType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT DISTINCT c.oneDepth FROM CategoryEntity c")
    List<String> findDistinctOneDepth();

    @Query("SELECT DISTINCT c.twoDepth FROM CategoryEntity c")
    List<String> findDistinctTwoDepth();

}
