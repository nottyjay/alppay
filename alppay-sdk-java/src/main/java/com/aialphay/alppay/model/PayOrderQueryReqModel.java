package com.aialphay.alppay.model;

import com.aialphay.alppay.ApiField;

/**
 * 支付查单请求实体类
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class PayOrderQueryReqModel extends AlppayObject {

  private static final long serialVersionUID = -5184554341263929245L;
  /** 商户订单号 */
  @ApiField("mchOrderNo")
  String mchOrderNo;
  /** 支付订单号 */
  @ApiField("payOrderId")
  String payOrderId;
  /** 商户号 */
  @ApiField("mchNo")
  private String mchNo;
  /** 应用ID */
  @ApiField("appId")
  private String appId;

  public String getMchNo() {
    return mchNo;
  }

  public void setMchNo(String mchNo) {
    this.mchNo = mchNo;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getMchOrderNo() {
    return mchOrderNo;
  }

  public void setMchOrderNo(String mchOrderNo) {
    this.mchOrderNo = mchOrderNo;
  }

  public String getPayOrderId() {
    return payOrderId;
  }

  public void setPayOrderId(String payOrderId) {
    this.payOrderId = payOrderId;
  }
}
