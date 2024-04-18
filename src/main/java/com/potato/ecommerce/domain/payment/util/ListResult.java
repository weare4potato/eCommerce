package com.potato.ecommerce.domain.payment.util;

import com.potato.ecommerce.domain.payment.dto.CommonResult;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends CommonResult {

    private List<T> data;
}
