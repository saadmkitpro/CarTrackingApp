package com.khanstech.ownerdriverapp.utils;

import android.util.Log;

import com.khanstech.ownerdriverapp.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OKHttp {

    private static String TAG = String.valueOf(R.string.app_name);
    private final MediaType JSON;
    private OkHttpClient client = null;
    private String responseBody = null;
    public int responseCode = 0;
    public String responseStatus = null;


    public OKHttp() {
        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    public String postCall(String url, String body) {
        try {
            RequestBody reqBody = RequestBody.create(JSON, body);
            Request request = new Request.Builder().url(url).post(reqBody).build();
            Response response = client.newCall(request).execute();
            responseCode = response.code();
            responseStatus = response.message().trim();
            responseBody = response.body().string().trim();

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return responseBody;
    }
}
