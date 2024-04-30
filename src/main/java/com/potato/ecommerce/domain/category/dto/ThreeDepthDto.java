package com.potato.ecommerce.domain.category.dto;

import com.potato.ecommerce.domain.category.entity.ThreeDepthEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThreeDepthDto {

    private Long id;
    private Long twoDepthId;
    private String threeDepth;

    public static ThreeDepthDto of(ThreeDepthEntity entity) {
        return new ThreeDepthDto(entity.getId(), entity.getTwoDepth().getId(),
            entity.getThreeDepth());
    }

}
