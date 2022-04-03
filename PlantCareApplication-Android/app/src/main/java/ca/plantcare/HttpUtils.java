/*
**Author: From 321 Tutorials
*/

package ca.plantcare;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    public static final String DEFAULT_BASE_URL = "http://10.0.2.2:8080/"; //change later

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        HttpUtils.baseUrl = baseUrl;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void postWithBody(Context context, String url, JSONObject body, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException, UnsupportedEncodingException {
        StringEntity stringEntity = new StringEntity(body.toString());
        client.post(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void put(String url, RequestParams params,  AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException, UnsupportedEncodingException {
        client.put(getAbsoluteUrl(url), params, responseHandler);
          }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
