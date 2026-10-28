package com.aialphay.alppay.pay.rqrs.payorder.payway;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.pay.rqrs.payorder.CommonPayDataRQ;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * none.
 *
 * @author nottyjay
 * @package com.aialphay.alppay.pay.rqrs.payorder.payway
 * @create 2021/11/15 17:52
 */
@Data
public class PPPcOrderRQ extends CommonPayDataRQ {

    /**
     * 商品描述信息
     **/
    @NotBlank(message = "取消支付返回站点")
    private String cancelUrl;

    public PPPcOrderRQ() {
        this.setWayCode(CS.PAY_WAY_CODE.PP_PC);
    }
}
