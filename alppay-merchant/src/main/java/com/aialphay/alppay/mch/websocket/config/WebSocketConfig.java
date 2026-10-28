package com.aialphay.alppay.mch.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*
 * 开启WebSocket支持
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/22 12:57
 */
@Configuration
public class WebSocketConfig {

  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
