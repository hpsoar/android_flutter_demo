package me.lattern.network;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class WS {
    public static abstract class OnResultListener<T> {
        public T convert(Object o, IDataConvertor convertor) {
            Class<T> clazz = ((Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]);

            return convertor.convert(o, clazz);
        }

        abstract void onResult(T data, Error error);
    }

    private static IHttpClient client;

    /*
     * convert network data(json/xml/?) to structured data type
     */
    private static IDataConvertor dataConvertor;

    private Context context_;
    private String url_;
    private Map<String, Object> parameters_;
    private IHttpClient.Method method_;
    private IHttpClient.RequestBodyType requestBodyType_;

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
        return new WS(null).method(IHttpClient.Method.GET);
    }

    public static WS post(String url) {
        return new WS(null).method(IHttpClient.Method.POST);
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

    public WS method(IHttpClient.Method method) {
        method_ = method;
        return this;
    }

    public WS requestBodyType(IHttpClient.RequestBodyType requestBodyType) {
        requestBodyType_ = requestBodyType;
        return this;
    }

    public <T> void get(String url, Map<String, Object> parameters, OnResultListener<T> listener) {
        method(IHttpClient.Method.GET).url(url).parameters(parameters).request(listener);
    }

    public <T> void post(String url, Map<String, Object> parameters,  OnResultListener<T> listener) {
        method(IHttpClient.Method.POST).url(url).parameters(parameters).request(listener);
    }

    private <T> void request(final OnResultListener<T> listener) {
        client.request(context_, url_, parameters_, method_, requestBodyType_,new IHttpClient.OnResultListener() {
            @Override
            public void onResult(Object data, Error error) {
                T r = listener.convert(data, dataConvertor);
                listener.onResult(r, error);
            }
        });
    }

    public <T> void start(Context context, OnResultListener<T> listener) {
        context_ = context;
        request(listener);
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
