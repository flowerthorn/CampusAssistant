package com.pocketmoney.net.okhttp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pocketmoney.net.callback.NetCallback;
import com.pocketmoney.net.frame.IClient;
import com.pocketmoney.net.frame.IClientBuilder;
import com.pocketmoney.net.frame.IRequest;
import com.pocketmoney.net.frame.ResponseHandler;
import com.pocketmoney.net.utils.UserAgentSetting;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangkang on 17/3/20.
 */

public class OkIClient extends IClient {

    private OkHttpClient mClient;

    public OkIClient(IClientBuilder builder) {
        super(builder);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.connectTimeout(builder.getTimeout(), builder.getTimeoutTimeUnit());

        okBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d("__request", chain.request().url().toString());
                Request originalRequest = chain.request();
                Request requestWithUserAgent = originalRequest.newBuilder()
                        .header("User-Agent", UserAgentSetting.getUserAgent())
                        .build();
                return chain.proceed(requestWithUserAgent);
            }
        });
        if (builder.getCookieSotre() != null) {
            okBuilder.cookieJar(new CookieJarImpl(builder.getCookieSotre()));
        }

        sslSetup(okBuilder);

        mClient = okBuilder.build();
    }

    // 忽略证书验证
    private void sslSetup(OkHttpClient.Builder okBuilder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private IRequest baseGet(String url, Object tag, NetCallback netCallback) {
        final Call call = mClient.newCall(new Request.Builder().url(url).tag(tag).build());
        call.enqueue(new ResponseCallback(netCallback));
        return new OkIRequest(call);
    }

    @NonNull
    private IRequest basePost(String url, Map<String, String> params, Object tag, NetCallback netCallback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.get(key);
                builder.add(key, value);
            }
        }

        Request request = new Request.Builder().url(url).tag(tag).post(builder.build()).build();
        final Call call = mClient.newCall(request);
        call.enqueue(new ResponseCallback(netCallback));
        return new OkIRequest(call);
    }

    @Override
    public IRequest get(Object tag, String url, NetCallback netCallback) {
        return baseGet(url, tag, netCallback);
    }

    @Override
    public IRequest post(Object tag, String url, Map<String, String> params, NetCallback netCallback) {
        return basePost(url, params, tag, netCallback);
    }

    @Override
    public void cancelRequest(Object tag) {
        for (Call call : mClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private static class ResponseCallback implements okhttp3.Callback {

        private final NetCallback netCallback;

        ResponseCallback(final NetCallback netCallback) {
            this.netCallback = netCallback;
        }

        @Override
        public void onFailure(final Call call, IOException e) {
            ResponseHandler.executeFailureCallback(call.request().url().toString(), e, netCallback);
        }

        @Override
        public void onResponse(final Call call, final Response response) throws IOException {
            ResponseHandler.executeSuccessCallback(call.request().url().toString(), new OkIResponse(response), netCallback);
        }

    }

}
