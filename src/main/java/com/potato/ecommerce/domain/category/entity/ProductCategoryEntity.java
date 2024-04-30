package com.potato.ecommerce.domain.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_categories")
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_depth_id")
    private OneDepthEntity oneDepth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "two_depth_id")
    private TwoDepthEntity twoDepth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "three_depth_id")
    private ThreeDepthEntity threeDepth;


}
