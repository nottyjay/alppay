package com.aialphay.alppay.pay.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * HttpServletRequest 信息打印工具类（全量输出，适合调试）
 */
public class RequestPrintUtil {

    /**
     * 打印 HttpServletRequest 所有信息
     * @param request 原生 HttpServletRequest 对象
     */
    public static void printAllRequestInfo(HttpServletRequest request) {
        try {
            System.out.println("===================================== HttpServletRequest 详细信息 =====================================");

            // 1. 请求行信息（方法、URL、协议等）
            System.out.println("\n【1. 请求行信息】");
            System.out.println("请求方法: " + request.getMethod());
            System.out.println("完整URL: " + request.getRequestURL().toString());
            System.out.println("请求URI: " + request.getRequestURI());
            System.out.println("协议版本: " + request.getProtocol());
            System.out.println("查询字符串(URL参数): " + (request.getQueryString() != null ? request.getQueryString() : "无"));
            System.out.println("字符编码: " + request.getCharacterEncoding());
            System.out.println("Content-Type: " + request.getContentType());

            // 2. 客户端/服务器信息
            System.out.println("\n【2. 客户端&服务器信息】");
            System.out.println("客户端IP: " + getClientRealIp(request)); // 获取真实IP（兼容代理/反向代理）
            System.out.println("客户端端口: " + request.getRemotePort());
            System.out.println("服务器IP: " + request.getLocalAddr());
            System.out.println("服务器端口: " + request.getLocalPort());
            System.out.println("User-Agent(浏览器/客户端): " + request.getHeader("User-Agent"));

            // 3. 请求头信息（遍历所有头）
            System.out.println("\n【3. 所有请求头】");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                System.out.printf("  %s: %s%n", headerName, headerValue);
            }

            // 4. 请求参数（URL参数 + 表单参数，POST x-www-form-urlencoded 适用）
            System.out.println("\n【4. 所有请求参数（URL+表单）】");
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.isEmpty()) {
                System.out.println("  无请求参数（若为JSON请求，参数在请求体中）");
            } else {
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String paramName = entry.getKey();
                    String[] paramValues = entry.getValue();
                    System.out.printf("  %s: %s%n", paramName, String.join(",", paramValues));
                }
            }

            // 5. Cookie 信息
            System.out.println("\n【5. 所有Cookie】");
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                System.out.println("  无Cookie");
            } else {
                for (Cookie cookie : cookies) {
                    System.out.printf("  名称: %s, 值: %s, 域名: %s, 路径: %s, 过期时间: %d%n",
                            cookie.getName(), cookie.getValue(), cookie.getDomain(),
                            cookie.getPath(), cookie.getMaxAge());
                }
            }

            // 6. 请求体信息（POST/PUT 等带请求体的请求，如JSON、表单数据）
            System.out.println("\n【6. 请求体内容】");
            printRequestBody(request);

            // 7. 其他补充信息
            System.out.println("\n【7. 其他信息】");
            System.out.println("上下文路径: " + request.getContextPath());
            System.out.println("Servlet路径: " + request.getServletPath());
            System.out.println("请求编码: " + request.getCharacterEncoding());
            System.out.println("Content-Length: " + (request.getContentLength() == -1 ? "未知" : request.getContentLength() + "字节"));

            System.out.println("===================================== HttpServletRequest 信息打印结束 =====================================");
        } catch (Exception e) {
            System.err.println("打印 Request 信息失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 打印请求体（兼容 JSON、表单、文本等格式）
     * 注意：流只能读取一次，若项目中已有拦截器读取过流，此处可能为空（需用 ContentCachingRequestWrapper 包装）
     */
    private static void printRequestBody(HttpServletRequest request) throws IOException {
        // 仅处理有请求体的方法（POST/PUT/PATCH/DELETE 等）
        String method = request.getMethod();
        if ("GET".equals(method) || "HEAD".equals(method)) {
            System.out.println("  GET/HEAD 方法无请求体");
            return;
        }

        // 读取请求体（Spring 提供的 StreamUtils 简化读取，无 Spring 可手动用 BufferedReader 读取）
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        if (bodyBytes.length == 0) {
            System.out.println("  请求体为空");
            return;
        }

        // 转换为字符串（UTF-8 编码）
        String requestBody = new String(bodyBytes, StandardCharsets.UTF_8);
        System.out.println("  " + requestBody);

        // 可选：若需重复读取流，需将流回写（此处为调试用，无需回写，若后续需使用流需回写）
        // request.setAttribute("requestBody", requestBody);
    }

    /**
     * 获取客户端真实IP（兼容代理、Nginx反向代理等场景）
     */
    private static String getClientRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多代理场景（x-forwarded-for 可能包含多个IP，取第一个）
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
