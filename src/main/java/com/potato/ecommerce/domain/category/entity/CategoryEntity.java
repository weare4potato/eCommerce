package com.potato.ecommerce.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "one_depth", nullable = false)
    private CategoryType oneDepth;

    @Column(nullable = false)
    private String oneDepthDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "two_depth", nullable = false)
    private CategoryType twoDepth;

    @Column(nullable = false)
    private String twoDepthDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "three_depth", nullable = false)
    private CategoryType threeDepth;

    @Column(nullable = false)
    private String threeDepthDescription;
}
