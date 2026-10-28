package com.aialphay.alppay.net;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aialphay.alppay.exception.APIException;
import com.aialphay.alppay.exception.AlppayException;
import com.aialphay.alppay.exception.InvalidRequestException;
import com.aialphay.alppay.request.AlppayRequest;
import com.aialphay.alppay.response.AlppayResponse;
import com.aialphay.alppay.util.JSONWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API资源抽象类
 * @author nottyjay
 * @site https://www.aialphay.com
 * @date 2021-06-08 11:00
 */
public abstract class APIResource  {

    public static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final Logger _log = LoggerFactory.getLogger(APIResource.class);
    private static HttpClient httpClient = new HttpURLConnectionClient();

    public static Class<?> getSelfClass() {
        return APIResource.class;
    }

    protected static String urlEncode(String str) {
        if (str == null) {
            return null;
        }

        try {
            return URLEncoder.encode(str, CHARSET.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
    }

    /**
     * 错误处理
     * @param response
     * @throws AlppayException
     */
    private static void handleAPIError(APIAlppayResponse response)
            throws AlppayException {

        String rBody = response.getResponseBody();
        int rCode = response.getResponseCode();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSONObject.parseObject(rBody);

        } catch (JSONException e) {
            raiseMalformedJsonError(rBody, rCode);
        }

        if(rCode == 404) {
            throw new InvalidRequestException(jsonObject.getString("status") + ", "
                    + jsonObject.getString("error") + ", "
                    + jsonObject.getString("path")
                    , rCode, null);
        }

    }

    private static void raiseMalformedJsonError(
            String responseBody, int responseCode) throws APIException {
        throw new APIException(
                String.format(
                        "Invalid response object from API: %s. (HTTP response code was %d)",
                        responseBody, responseCode),
                null,
                null,
                responseCode,
                null);
    }

    public <T extends AlppayResponse> T execute(
            AlppayRequest<T> request,
            RequestMethod method,
            String url) throws AlppayException {

        String jsonParam = new JSONWriter().write(request.getBizModel(), true);

        JSONObject params = JSONObject.parseObject(jsonParam);
        request.getRequestOptions();
        APIAlppayRequest apiAlppayRequest = new APIAlppayRequest(method, url, params, request.getRequestOptions());
        if(_log.isDebugEnabled()) _log.debug("Alppay_SDK_REQ: url={}, data={}", apiAlppayRequest.getUrl(), JSONObject.toJSONString(apiAlppayRequest.getParams()));
        APIAlppayResponse response = httpClient.requestWithRetries(apiAlppayRequest);
        int responseCode = response.getResponseCode();
        String responseBody = response.getResponseBody();
        if(_log.isDebugEnabled()) _log.debug("Alppay_SDK_RES: code={}, body={}", responseCode, responseBody);
        if (responseCode != 200) {
            handleAPIError(response);
        }

        T resource = null;

        try {
            resource = JSONObject.parseObject(responseBody, request.getResponseClass());
        } catch (JSONException e) {
            raiseMalformedJsonError(responseBody, responseCode);
        }

        return resource;
    }

    protected enum RequestMethod {
        GET,
        POST,
        DELETE,
        PUT
    }
}
