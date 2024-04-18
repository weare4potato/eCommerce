package com.potato.ecommerce.domain.payment.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean success;

    private int code;

    private String message;

}
