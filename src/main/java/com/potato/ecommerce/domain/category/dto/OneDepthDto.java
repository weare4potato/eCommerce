package com.potato.ecommerce.domain.category.dto;

import com.potato.ecommerce.domain.category.entity.OneDepthEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OneDepthDto {

    private Long id;
    private String oneDepth;

    public static OneDepthDto of(OneDepthEntity entity) {
        return new OneDepthDto(entity.getId(), entity.getOneDepth());
    }

}
