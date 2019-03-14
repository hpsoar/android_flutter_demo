package me.lattern.flutter_utils;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Roger Huang on 2019/1/21.
 */

public interface CYBridge {
    interface Callback {
        void onResult(JSONObject result, Error error);
    }

    void call(String method, JSONObject parameters, Callback callback);

    boolean openNative(Context context, String path, JSONObject parameters);

    boolean openFlutter(Context context, String path, JSONObject parameters);

    void post(Context context, String path, JSONObject parameters, Callback callback);

    void get(Context context, String path, JSONObject parameters, Callback callback);
}
