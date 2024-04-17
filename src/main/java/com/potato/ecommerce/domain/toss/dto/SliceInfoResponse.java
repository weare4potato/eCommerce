package com.potato.ecommerce.domain.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class SliceInfoResponse {

    private Pageable pageable;

    private int size;

    private boolean hasNext;
}
