package com.aialphay.alppay.components.mq.vender;

/**
 * MQ 消息接收器 接口定义
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/7/21 16:09
 */
public interface IMQMsgReceiver {

  /** 接收消息 * */
  void receiveMsg(String msg);
}
