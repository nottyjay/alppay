package com.aialphay.alppay.net;

import com.aialphay.alppay.exception.APIConnectionException;
import com.aialphay.alppay.util.StreamUtils;
import com.aialphay.alppay.util.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpURL连接客户端
 *
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public class HttpURLConnectionClient extends HttpClient {

  public HttpURLConnectionClient() {
    super();
  }

  static HttpHeaders getHeaders(APIAlppayRequest request) {
    Map<String, List<String>> userAgentHeadersMap = new HashMap<>();

    userAgentHeadersMap.put(
        "User-Agent",
        Collections.singletonList(buildUserAgentString(request.getOptions().getVersion())));
    userAgentHeadersMap.put(
        "X-Alppay-Client-User-Agent",
        Collections.singletonList(
            buildXAlppayClientUserAgentString(request.getOptions().getVersion())));

    return request.getHeaders().withAdditionalHeaders(userAgentHeadersMap);
  }

  private static HttpURLConnection createAlppayConnection(APIAlppayRequest request)
      throws IOException {
    HttpURLConnection conn = (HttpURLConnection) request.url.openConnection();

    conn.setConnectTimeout(request.options.getConnectTimeout());
    conn.setReadTimeout(request.options.getReadTimeout());
    conn.setUseCaches(false);
    for (Map.Entry<String, List<String>> entry : getHeaders(request).map().entrySet()) {
      conn.setRequestProperty(entry.getKey(), StringUtils.join(",", entry.getValue()));
    }

    conn.setRequestMethod(request.method.name());

    // 如有其他业务参数，可在此处增加

    if (request.content != null) {
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", request.content.contentType);

      try (OutputStream output = conn.getOutputStream()) {
        output.write(request.content.byteArrayContent);
      }
    }

    return conn;
  }

  @Override
  public APIAlppayResponse request(APIAlppayRequest request) throws APIConnectionException {
    HttpURLConnection conn = null;

    try {
      conn = createAlppayConnection(request);

      // trigger the request
      int responseCode = conn.getResponseCode();
      HttpHeaders headers = HttpHeaders.of(conn.getHeaderFields());
      String responseBody;

      if (responseCode >= 200 && responseCode < 300) {
        responseBody = StreamUtils.readToEnd(conn.getInputStream(), APIResource.CHARSET);
      } else {
        responseBody = StreamUtils.readToEnd(conn.getErrorStream(), APIResource.CHARSET);
      }

      return new APIAlppayResponse(responseCode, responseBody, headers);
    } catch (IOException e) {
      throw new APIConnectionException(
          String.format("请求Alppay(%s)异常,请检查网络或重试.异常信息:%s", request.getUrl(), e.getMessage()), e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
  }
}
