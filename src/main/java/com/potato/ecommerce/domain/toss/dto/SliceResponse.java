package com.potato.ecommerce.domain.toss.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SliceResponse<T> {

    private List<T> chargingHistoryDtoList;
    private SliceInfoResponse sliceInfoResponse;
}
