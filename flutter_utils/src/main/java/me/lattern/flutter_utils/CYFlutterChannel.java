package me.lattern.flutter_utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

/**
 * Created by Roger Huang on 2019/1/19.
 */

public class CYFlutterChannel {
    static CYBridge sBridge = null;

    public static void setBridge(CYBridge bridge) {
        sBridge = bridge;
    }

    interface PopResult {
        void onResult(Boolean completed);
    }

    public static String METHOD_CHANNEL = "flutter.chunyu.com.channel.method";
    public static CYBridge bridge = null;

    private MethodChannel methodChannel = null;
    private Context mConext;

    public CYFlutterChannel(final Context context, PluginRegistry registry, FlutterView flutterView, String channel) {
        mConext = context;

        GeneratedPluginRegistrant.registerWith(registry);

        methodChannel = new MethodChannel(flutterView, channel, JSONMethodCodec.INSTANCE);

        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) {
                Log.i("flutter channel", "" + methodCall.method + ", " + methodCall.arguments);

                if (methodCall.method.equals("openNative")) {
                    if (openNative(mConext, (JSONObject)methodCall.arguments)) {
                        result.success(makeResult(null, null));
                    } else {
                        result.success(makeResult(null, new Error("invalid parameters or failed to handle path")));
                    }
                } else if (methodCall.method.equals("openFlutter")) {
                    if (openFlutterPage(context, (JSONObject) methodCall.arguments)) {
                        result.success(makeResult(null, null));
                    } else {
                        result.success(makeResult(null, new Error("invalid parameters")));
                    }
                } else {
                    if (bridge == null) {
                        bridge = sBridge;
                    }

                    if (bridge != null) {
                        JSONObject parameters = (JSONObject) methodCall.arguments;
                        if (methodCall.method.equals("GET")) {

                            String path = parameters.optString("url");
                            JSONObject p = parameters.optJSONObject("parameters");

                            bridge.get(mConext, path, p, new CYBridge.Callback() {
                                @Override
                                public void onResult(JSONObject object, Error error) {
                                    result.success(makeResult(object, error));
                                }
                            });
                        } else {
                            bridge.call(methodCall.method, parameters, new CYBridge.Callback() {
                                @Override
                                public void onResult(JSONObject r, Error error) {
                                    result.success(makeResult(r, error));
                                }
                            });
                        }
                    } else {
                        result.success(makeResult(null, new Error("unsupported call")));
                    }
                }
            }
        });
    }

    // flutter导航的返回
    public void onBack(final PopResult callback) {
        methodChannel.invokeMethod("pop", null, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object o) {
                if (callback != null) {
                    callback.onResult(o.equals("completed"));
                }
            }

            @Override
            public void error(String s, @Nullable String s1, @Nullable Object o) {

            }

            @Override
            public void notImplemented() {

            }
        });
    }

    public static boolean openFlutterPage(Context context, JSONObject parameters) {
        if (parameters == null) {
            return false;
        }

        String route = parameters.optString("path");

        Intent intent = new Intent(context, CYFlutterActivity.class);
        intent.setAction("android.intent.action.RUN");
        intent.putExtra("route", route);
        context.startActivity(intent);

        return true;
    }

    private static boolean openNative(Context context, JSONObject parameters) {
        if (parameters == null) return false;

        if (bridge == null) return false;

        String path = parameters.optString("path");

        bridge.openNative(context, path, parameters);

        return true;
    }

    public static JSONObject makeResult(JSONObject object, Error error) {
        JSONObject r = new JSONObject();
        try {
            r.put("success", error == null);
            if (object != null) {
                r.put("data", object);
            }
            if (error != null) {
                r.put("error", error.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }
}
