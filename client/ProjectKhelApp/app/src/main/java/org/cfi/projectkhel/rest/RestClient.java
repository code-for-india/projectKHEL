package org.cfi.projectkhel.rest;

import android.content.Context;

import com.loopj.android.http.*;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

/**
 * KHEL REST Client.
 */
public class RestClient {

  private static final String BASE_URL = "http://localhost:8901/khel/api/";

  private static AsyncHttpClient client = new AsyncHttpClient();

  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    client.get(getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    client.post(getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(String url, StringEntity body, AsyncHttpResponseHandler responseHandler) {
    client.post(null, getAbsoluteUrl(url), body, "application/json", responseHandler);
  }

  private static String getAbsoluteUrl(String relativeUrl) {
    return BASE_URL + relativeUrl;
  }
}