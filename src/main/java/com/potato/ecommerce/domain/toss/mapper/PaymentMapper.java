package com.potato.ecommerce.domain.toss.mapper;

import com.potato.ecommerce.domain.toss.dto.ChargingHistoryDto;
import com.potato.ecommerce.domain.toss.entity.PaymentEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    default List<ChargingHistoryDto> chargingHistoryToChargingHistoryResponse(
        List<PaymentEntity> chargingHistories) {
        if (chargingHistories == null) {
            return null;
        }

        return chargingHistories.stream()
            .map(chargingHistory -> {
                return ChargingHistoryDto.builder()
                    .paymentHistoryId(chargingHistory.getPaymentId())
                    .amount(chargingHistory.getAmount())
                    .orderName(chargingHistory.getOrderName())
                    .createAt(chargingHistory.getCreatedAt())
                    .isPaySuccessYN(chargingHistory.isPaySuccessYN())
                    .build();
            }).toList();
    }
}
