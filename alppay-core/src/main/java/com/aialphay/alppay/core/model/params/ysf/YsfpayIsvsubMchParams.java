package com.aialphay.alppay.core.model.params.ysf;

import com.aialphay.alppay.core.model.params.IsvsubMchParams;
import lombok.Data;

/*
 * 云闪付 配置信息
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 18:02
 */
@Data
public class YsfpayIsvsubMchParams extends IsvsubMchParams {

  private String merId; // 商户编号
}
