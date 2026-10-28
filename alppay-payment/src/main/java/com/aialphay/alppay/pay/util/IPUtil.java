package com.aialphay.alppay.pay.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IPUtil {

  /**
   * 获取本机IP地址
   * @return 本机IP地址字符串
   */
  public static String getLocalIpAddress() {
    try {
      // 尝试获取所有网络接口
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

      while (networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();

        // 跳过虚拟网卡和未启用的网卡
        if (networkInterface.isLoopback() || !networkInterface.isUp()) {
          continue;
        }

        // 获取该网卡的所有IP地址
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

        while (inetAddresses.hasMoreElements()) {
          InetAddress inetAddress = inetAddresses.nextElement();

          // 跳过IPv6地址和环回地址
          if (inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isMulticastAddress()) {
            continue;
          }

          String hostAddress = inetAddress.getHostAddress();
          // 过滤掉127开头的本地地址
          if (hostAddress != null && !hostAddress.startsWith("127.")) {
            return hostAddress;
          }
        }
      }

      // 如果没有找到合适的IP，返回本地主机地址
      return InetAddress.getLocalHost().getHostAddress();
    } catch (SocketException | UnknownHostException e) {
      // 如果发生异常，返回默认值
      return "127.0.0.1";
    }
  }
}
