package com.potato.ecommerce.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPayment {

    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String requestedAt;
    private String approvedAt;
    private Boolean useEscrow;
    private Boolean cultureExpense;
    private String type;
    private EasyPay easyPay;
    private String country;
    private Receipt receipt;
    private Checkout checkout;
    private String currency;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private String method;
    private String version;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EasyPay {

        private String provider;
        private Integer amount;
        private Integer discountAmount;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Receipt {

        private String url;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Checkout {

        private String url;
    }
}
