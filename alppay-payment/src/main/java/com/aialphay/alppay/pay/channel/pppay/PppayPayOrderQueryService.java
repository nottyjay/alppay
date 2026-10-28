package com.aialphay.alppay.pay.channel.pppay;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.PayOrder;
import com.aialphay.alppay.pay.channel.IPayOrderQueryService;
import com.aialphay.alppay.pay.model.MchAppConfigContext;
import com.aialphay.alppay.pay.rqrs.msg.ChannelRetMsg;
import com.aialphay.alppay.pay.service.ConfigContextQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * none.
 *
 * @author nottyjay
 * @package com.aialphay.alppay.pay.channel.pppay
 * @create 2021/11/15 21:02
 */
@Service
public class PppayPayOrderQueryService implements IPayOrderQueryService {

  @Autowired private ConfigContextQueryService configContextQueryService;

  @Override
  public String getIfCode() {
    return CS.IF_CODE.PPPAY;
  }

  @Override
  public ChannelRetMsg query(PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
      throws Exception {
    return configContextQueryService
        .getPaypalWrapper(mchAppConfigContext)
        .processOrder(null, payOrder);
  }
}
