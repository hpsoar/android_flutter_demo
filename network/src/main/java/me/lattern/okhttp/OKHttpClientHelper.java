package me.lattern.okhttp;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import me.lattern.core.data.JSONConvertor;
import me.lattern.network.DefaultCookieStore;
import me.lattern.network.IHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpClientHelper implements IHttpClient {
    // TODO: create enum for method & body type

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;

    /*
     * setup OkHttpClient
     * 1. cookie
     * 2. dns
     * 3. verify ssl
     *
     * override this when you need to build a refresh-new client
     */
    public void setup(Context context) {
        CookieStore cookieStore = createCookieStore(context);

        CookieManager manager = new CookieManager( cookieStore, CookiePolicy.ACCEPT_ALL );

        CookieHandler.setDefault(manager);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.cookieJar(createCookieJar())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .dns(createDns())
                .pingInterval(5, TimeUnit.SECONDS);

        client = builder.build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    /*
     * override the following to customize
     */

    /*
     * dns
     */
    protected Dns createDns() {
        return new DefaultHttpDns(null);
    }

    /*
     * dns
     */
    protected CookieStore createCookieStore(Context context) {
        return new DefaultCookieStore(context);
    }

    /*
     * dns
     */
    protected CookieJar createCookieJar() {
        return new DefaultCookieJar(CookieHandler.getDefault());
    }

    protected Request buildRequest(String url, Map<String, Object> parameters, Method method, RequestBodyType bodyType) {
        if (method.equals(Method.GET)) {
            HttpUrl.Builder httpBuilder = createUrlBuilder(url, parameters, bodyType);

            return new Request.Builder().url(httpBuilder.build()).build();
        }

        if (method.equals(Method.POST)) {
            return new Request.Builder().url(url) .post(createRequestBody(parameters, bodyType)).build();
        }

        return null;
    }

    /*
     * override this for different body type
     * TODO: request-wise body type?
     */
    protected RequestBody createRequestBody(Map<String, Object> parameters, RequestBodyType bodyType) {
        String s = JSONConvertor.o2JsonS(parameters);
        RequestBody body = RequestBody.create(JSON, s);
        return body;
    }

    /*
     * TODO: request-wise body type?
     */
    protected HttpUrl.Builder createUrlBuilder(String url, Map<String, Object> parameters, RequestBodyType bodyType) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();

        if (parameters != null) {
            JSONObject o = JSONConvertor.o2Json(parameters);
            while (o != null && o.keys().hasNext()) {
                String key = o.keys().next();
                try {
                    Object value = o.get(key);
                    httpBuilder.addQueryParameter(key, value.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return httpBuilder;
    }

    @Override
    public void request(Context context, String url, Map<String, Object> parameters, Method method, RequestBodyType bodyType, final OnResultListener listener) {
        Request request = buildRequest(url, parameters, method, bodyType);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onResult(null, new Error(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onResult(response.body().string(), null);
            }
        });
    }
}
