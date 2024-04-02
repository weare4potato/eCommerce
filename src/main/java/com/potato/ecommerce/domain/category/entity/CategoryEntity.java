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
    @Column(name = "one_depth")
    @NotNull
    private CategoryType oneDepth;

    @Enumerated(EnumType.STRING)
    @Column(name = "two_depth")
    @NotNull
    private CategoryType twoDepth;

    @Enumerated(EnumType.STRING)
    @Column(name = "three_depth")
    @NotNull
    private CategoryType threeDepth;
}
