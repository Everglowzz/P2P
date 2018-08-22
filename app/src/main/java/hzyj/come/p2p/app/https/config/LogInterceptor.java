package hzyj.come.p2p.app.https.config;

import android.content.Context;
import android.util.Log;


import java.io.IOException;

import hzyj.come.p2p.app.log.LogLevel;
import hzyj.come.p2p.app.log.Logger;
import hzyj.come.p2p.app.utils.Tools;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Youga on 2016/4/28.
 */
public class LogInterceptor implements Interceptor {

    private static final String F_BREAK = " %n";
    private static final String F_URL = " %s";
    private static final String F_TIME = " in %.1fms";
    private static final String F_HEADERS = "%s";
    private static final String F_RESPONSE = F_BREAK + "Response: %d";
    private static final String F_BODY = "body: %s";

    private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
    private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
    private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
    private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
    //    private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;
    public static final String TAG = "LogInterceptor";

    public LogInterceptor(Context context) {
        Logger.init(TAG).logLevel(Tools.isDebugGable(context) ? LogLevel.FULL : LogLevel.NONE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        double time = (t2 - t1) / 1e6d;

        switch (request.method()) {
            case "GET":
                Logger.d("GET " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), response.code(), response.headers());
                break;
            case "POST":
                Logger.d("POST " + F_REQUEST_WITH_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), stringifyRequestBody(request), response.code(), response.headers());
                break;
            case "PUT":
                Logger.d("PUT " + F_REQUEST_WITH_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), request.body().toString(), response.code(), response.headers());
                break;
            case "DELETE":
                Logger.d("DELETE " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), response.code(), response.headers());
                break;
        }

        if (response.body() != null) {
            MediaType contentType = response.body().contentType();
            String bodyString = response.body().string();
            interceptorResponse(response.newBuilder().body(ResponseBody.create(contentType, bodyString)).build());
            return response.newBuilder().body(ResponseBody.create(contentType, bodyString)).build();
        } else {
            return response;
        }
    }


    private static String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private void interceptorResponse(Response response) {
        try {
            if (response.code() == 200) {
                Logger.json(response.body().string());
            } else {
                Log.e(TAG, response.body().string());
            }
        } catch (Exception e) {
            try {
                Logger.e("Exception:" + response.body().string());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
