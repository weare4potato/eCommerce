package com.potato.ecommerce.domain.category.dto;

import com.potato.ecommerce.domain.category.entity.TwoDepthEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TwoDepthDto {

    private Long id;
    private Long oneDepthId;
    private String twoDepth;

    public static TwoDepthDto of(TwoDepthEntity Entity) {
        return new TwoDepthDto(Entity.getId(), Entity.getOneDepth().getId(), Entity.getTwoDepth());
    }
}
