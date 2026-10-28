package com.aialphay.alppay.pay.service;

import com.aialphay.alppay.core.constants.CS;
import com.aialphay.alppay.core.entity.MchApp;
import com.aialphay.alppay.core.entity.MchInfo;
import com.aialphay.alppay.core.entity.PayInterfaceConfig;
import com.aialphay.alppay.core.model.params.IsvParams;
import com.aialphay.alppay.core.model.params.IsvsubMchParams;
import com.aialphay.alppay.core.model.params.NormalMchParams;
import com.aialphay.alppay.core.model.params.alipay.AlipayIsvParams;
import com.aialphay.alppay.core.model.params.alipay.AlipayNormalMchParams;
import com.aialphay.alppay.core.model.params.icbc.IcbcNormalMchParams;
import com.aialphay.alppay.core.model.params.pppay.PppayNormalMchParams;
import com.aialphay.alppay.core.model.params.wxpay.WxpayIsvParams;
import com.aialphay.alppay.core.model.params.wxpay.WxpayNormalMchParams;
import com.aialphay.alppay.pay.model.*;
import com.aialphay.alppay.pay.model.*;
import com.aialphay.alppay.service.impl.MchAppService;
import com.aialphay.alppay.service.impl.MchInfoService;
import com.aialphay.alppay.service.impl.PayInterfaceConfigService;
import com.aialphay.alppay.service.impl.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 配置信息查询服务 （兼容 缓存 和 直接查询方式）
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/11/18 14:41
 */
@Slf4j
@Service
public class ConfigContextQueryService {

  @Autowired ConfigContextService configContextService;
  @Autowired private MchInfoService mchInfoService;
  @Autowired private MchAppService mchAppService;
  @Autowired private PayInterfaceConfigService payInterfaceConfigService;

  private boolean isCache() {
    return SysConfigService.IS_USE_CACHE;
  }

  public MchApp queryMchApp(String mchNo, String mchAppId) {

    if (isCache()) {
      return configContextService.getMchAppConfigContext(mchNo, mchAppId).getMchApp();
    }

    return mchAppService.getOneByMch(mchNo, mchAppId);
  }

  public MchAppConfigContext queryMchInfoAndAppInfo(String mchAppId) {
    return queryMchInfoAndAppInfo(mchAppService.getById(mchAppId).getMchNo(), mchAppId);
  }

  public MchAppConfigContext queryMchInfoAndAppInfo(String mchNo, String mchAppId) {

    if (isCache()) {
      return configContextService.getMchAppConfigContext(mchNo, mchAppId);
    }

    MchInfo mchInfo = mchInfoService.getById(mchNo);
    MchApp mchApp = queryMchApp(mchNo, mchAppId);

    if (mchInfo == null || mchApp == null) {
      return null;
    }

    MchAppConfigContext result = new MchAppConfigContext();
    result.setMchInfo(mchInfo);
    result.setMchNo(mchNo);
    result.setMchType(mchInfo.getType());

    result.setMchApp(mchApp);
    result.setAppId(mchAppId);

    return result;
  }

  public NormalMchParams queryNormalMchParams(String mchNo, String mchAppId, String ifCode) {

    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchNo, mchAppId)
          .getNormalMchParamsByIfCode(ifCode);
    }

    // 查询商户的所有支持的参数配置
    PayInterfaceConfig payInterfaceConfig =
        payInterfaceConfigService.getOne(
            PayInterfaceConfig.gw()
                .select(PayInterfaceConfig::getIfCode, PayInterfaceConfig::getIfParams)
                .eq(PayInterfaceConfig::getState, CS.YES)
                .eq(PayInterfaceConfig::getInfoType, CS.INFO_TYPE_MCH_APP)
                .eq(PayInterfaceConfig::getInfoId, mchAppId)
                .eq(PayInterfaceConfig::getIfCode, ifCode));

    if (payInterfaceConfig == null) {
      return null;
    }

    return NormalMchParams.factory(
        payInterfaceConfig.getIfCode(), payInterfaceConfig.getIfParams());
  }

  public IsvsubMchParams queryIsvsubMchParams(String mchNo, String mchAppId, String ifCode) {

    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchNo, mchAppId)
          .getIsvsubMchParamsByIfCode(ifCode);
    }

    // 查询商户的所有支持的参数配置
    PayInterfaceConfig payInterfaceConfig =
        payInterfaceConfigService.getOne(
            PayInterfaceConfig.gw()
                .select(PayInterfaceConfig::getIfCode, PayInterfaceConfig::getIfParams)
                .eq(PayInterfaceConfig::getState, CS.YES)
                .eq(PayInterfaceConfig::getInfoType, CS.INFO_TYPE_MCH_APP)
                .eq(PayInterfaceConfig::getInfoId, mchAppId)
                .eq(PayInterfaceConfig::getIfCode, ifCode));

    if (payInterfaceConfig == null) {
      return null;
    }

    return IsvsubMchParams.factory(
        payInterfaceConfig.getIfCode(), payInterfaceConfig.getIfParams());
  }

  public IsvParams queryIsvParams(String isvNo, String ifCode) {

    if (isCache()) {
      IsvConfigContext isvConfigContext = configContextService.getIsvConfigContext(isvNo);
      return isvConfigContext == null ? null : isvConfigContext.getIsvParamsByIfCode(ifCode);
    }

    // 查询商户的所有支持的参数配置
    PayInterfaceConfig payInterfaceConfig =
        payInterfaceConfigService.getOne(
            PayInterfaceConfig.gw()
                .select(PayInterfaceConfig::getIfCode, PayInterfaceConfig::getIfParams)
                .eq(PayInterfaceConfig::getState, CS.YES)
                .eq(PayInterfaceConfig::getInfoType, CS.INFO_TYPE_ISV)
                .eq(PayInterfaceConfig::getInfoId, isvNo)
                .eq(PayInterfaceConfig::getIfCode, ifCode));

    if (payInterfaceConfig == null) {
      return null;
    }

    return IsvParams.factory(payInterfaceConfig.getIfCode(), payInterfaceConfig.getIfParams());
  }

  public AlipayClientWrapper getAlipayClientWrapper(MchAppConfigContext mchAppConfigContext) {

    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId())
          .getAlipayClientWrapper();
    }

    if (mchAppConfigContext.isIsvsubMch()) {

      AlipayIsvParams alipayParams =
          (AlipayIsvParams)
              queryIsvParams(mchAppConfigContext.getMchInfo().getIsvNo(), CS.IF_CODE.ALIPAY);
      return AlipayClientWrapper.buildAlipayClientWrapper(alipayParams);
    } else {

      AlipayNormalMchParams alipayParams =
          (AlipayNormalMchParams)
              queryNormalMchParams(
                  mchAppConfigContext.getMchNo(),
                  mchAppConfigContext.getAppId(),
                  CS.IF_CODE.ALIPAY);
      return AlipayClientWrapper.buildAlipayClientWrapper(alipayParams);
    }
  }

  public WxServiceWrapper getWxServiceWrapper(MchAppConfigContext mchAppConfigContext) {

    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId())
          .getWxServiceWrapper();
    }

    if (mchAppConfigContext.isIsvsubMch()) {

      WxpayIsvParams wxParams =
          (WxpayIsvParams)
              queryIsvParams(mchAppConfigContext.getMchInfo().getIsvNo(), CS.IF_CODE.WXPAY);
      return WxServiceWrapper.buildWxServiceWrapper(wxParams);
    } else {

      WxpayNormalMchParams wxParams =
          (WxpayNormalMchParams)
              queryNormalMchParams(
                  mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), CS.IF_CODE.WXPAY);
      return WxServiceWrapper.buildWxServiceWrapper(wxParams);
    }
  }

  public PaypalWrapper getPaypalWrapper(MchAppConfigContext mchAppConfigContext) {
    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId())
          .getPaypalWrapper();
    }
    PppayNormalMchParams ppPayNormalMchParams =
        (PppayNormalMchParams)
            queryNormalMchParams(
                mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), CS.IF_CODE.PPPAY);
    ;
    return PaypalWrapper.buildPaypalWrapper(ppPayNormalMchParams);
  }

  public IcbcWrapper getIcbcWrapper(MchAppConfigContext mchAppConfigContext) {
    if (isCache()) {
      return configContextService
          .getMchAppConfigContext(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId())
          .getIcbcWrapper();
    }
    IcbcNormalMchParams icbcNormalMchParams =
        (IcbcNormalMchParams)
            queryNormalMchParams(
                mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), CS.IF_CODE.ICBC);
    ;
    return IcbcWrapper.buildIcbcWrapper(icbcNormalMchParams);
  }
}
