package me.lattern.network;

import android.content.Context;

import java.util.Map;

public interface IHttpClient<T> {
    interface OnResultListener<T> {
        void onResult(T data, Error error);
    }

    void request(Context context, String url, Map<String, Object> parameters, String method, OnResultListener listener);
}
