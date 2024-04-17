package com.potato.ecommerce.domain.toss.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChargingHistoryDto {

    private Long paymentHistoryId;

    @NotNull
    private Long amount;

    @NotNull
    private String orderName;

    private boolean isPaySuccessYN;

    private LocalDateTime createAt;
}
