package me.lattern.network;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/*
 * 1. global configuration
 *      1. client: setClient
 *      2. data convertor: setDataConvertor
 * 2. request-wise configuration
 *      1. client: TODO:
 *      2. data convertor: override OnResultListener.convert
 */
public class WS {
    public static abstract class OnResultListener<T> {
        public T convert(Object o, IDataConvertor convertor) {
            Class<T> clazz = ((Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]);

            return convertor.convert(o, clazz);
        }

        public abstract void onResult(T data, Error error);
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

    public static WS get(String url) {
        return new WS().method(IHttpClient.Method.GET);
    }

    public static WS post(String url) {
        return new WS().method(IHttpClient.Method.POST);
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

    private <T> void request(final OnResultListener<T> listener) {
        method_ = method_ == null ? IHttpClient.Method.GET : method_;

        requestBodyType_ = requestBodyType_ == null ? IHttpClient.RequestBodyType.JSON : requestBodyType_;

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
        WS.get(null).parameters(null).start(null, new OnResultListener<WS>() {
            @Override
            public void onResult(WS data, Error error) {

            }
        });
    }
}
