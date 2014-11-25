package com.satoshidice.kyle.satoshidiceplayer.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HttpUtil {

    public static final String BASE_URL = "https://session.satoshidice.com/userapi";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, JsonHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        //
        return BASE_URL + relativeUrl;
    }

}