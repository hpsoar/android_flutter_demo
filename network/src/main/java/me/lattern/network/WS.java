package me.lattern.network;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class WS {
    public static abstract class OnResultListener<T> implements IHttpClient.OnResultListener<T> {
        public T convert(Object o, IDataConvertor convertor) {
            Class<T> clazz = ((Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]);

            return convertor.convert(o, clazz);
        }
    }

    private static IHttpClient client;

    /*
     * convert network data(json/xml/?) to structured data type
     */
    private static IDataConvertor dataConvertor;

    private Context context_;
    private String url_;
    private Map<String, Object> parameters_;
    private String method_;

    /*
     * independent on network data type,
     * the user is responsible to this
     */
    public static void setDataConvertor(IDataConvertor dc) {
        dataConvertor = dc;
    }

    /*
     * independent on HttpClient,
     * the user is responsible for this
     */
    public static void setClient(IHttpClient client) {
        WS.client = client;
    }

    public WS(Context context) {
        context_ = context;
    }

    public static WS get(String url) {
        return new WS(null).method("GET");
    }

    public static WS post(String url) {
        return new WS(null).method("POST");
    }

    public WS url(String url) {
        url_ = url;
        return this;
    }

    public WS parameters(Map<String, Object> parameters) {
        parameters_ = parameters;
        return this;
    }

    public WS parameter(String k, Object v) {
        if (parameters_ == null) {
            parameters_ = new HashMap<>();
        }
        parameters_.put(k, v);

        return this;
    }

    public WS method(String method) {
        method_ = method;
        return this;
    }

    public <T> void get(String url, Map<String, Object> parameters, OnResultListener<T> listener) {
        request(url, parameters, "GET", listener);
    }

    public <T> void post(String url, Map<String, Object> parameters,  OnResultListener<T> listener) {
        request(url, parameters, "POST", listener);
    }

    private <T> void request(String url, Map<String, Object> parameters, String method, final OnResultListener<T> listener) {
        client.request(context_, url, parameters, method, new IHttpClient.OnResultListener() {
            @Override
            public void onResult(Object data, Error error) {
                T r = listener.convert(data, dataConvertor);
                listener.onResult(r, error);
            }
        });
    }

    public <T> void start(Context context, OnResultListener<T> listener) {
        context_ = context;

        request(url_, parameters_, method_, listener);
    }

    public void test() {
        new WS(null).get(null, null, new OnResultListener<WS>() {
            @Override
            public void onResult(WS data, Error error) {

            }
        });

        WS.get(null).parameters(null).start(null, new OnResultListener<WS>() {
            @Override
            public void onResult(WS data, Error error) {

            }
        });
    }
}
