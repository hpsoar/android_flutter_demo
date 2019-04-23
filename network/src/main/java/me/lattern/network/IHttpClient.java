package me.lattern.network;

import android.content.Context;

import java.util.Map;

public interface IHttpClient {
    enum Method {
        GET("GET"),
        POST("POST");

        private String value;

        private Method(String v) {
            value = v;
        }

        public String rawValue() {
            return value;
        }
    }

    enum RequestBodyType {
        JSON("json"),
        XML("xml");

        private String value;

        private RequestBodyType(String v) {
            value = v;
        }

        public String rawValue() {
            return value;
        }
    }

    interface OnResultListener {
        void onResult(Object data, Error error);
    }

    void request(Context context,
                 String url,
                 Map<String, Object> parameters,
                 Method method,
                 RequestBodyType bodyType,
                 OnResultListener listener);
}
