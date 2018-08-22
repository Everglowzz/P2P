package hzyj.come.p2p.app.https.okhttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sxjs.common.widget.percentlayout.BuildConfig;


import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.ListCallBack;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.entity.Entity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Youga on 2015/8/26.
 */
public class GsonRequest extends HttpRequest {

    private Gson gson;
    private ListCallBack mListCallBack;
    @Inject
    public GsonRequest() {
    }

    private Class mClazz;

    public GsonRequest(Context context, Class clazz, ListCallBack listCallBack) {
        this(context, clazz, listCallBack, null);
    }

    public GsonRequest(Context context, Class clazz, ListCallBack listCallBack, Gson gson) {
        this.mClazz = clazz;
        this.mListCallBack = listCallBack;
        this.gson = gson == null ? mGson : gson;
    }

    public void obtainList(String url, final HashMap<String, String> params) {

        Request request = new Request.Builder()
                .url(url)
                .post(appendPageHeader(params))
                .tag(MyApplication.APP)
                .build();

        if (!Tools.checkNetworkState(MyApplication.APP)) {
            mListCallBack.onFailure(noInternet, params.get(pagetype_key));
            return;
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailedListCallback(mListCallBack, genericError, params.get(pagetype_key));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    Entity entity = gson.fromJson(json, Entity.class);
                    if (entity.getCode() == ACCOUNT_EXCEPTION) {
                        sendAlertCallback(MyApplication.APP, entity.getCode(), entity.getMessage());
                    } else {
                        Object result = gson.fromJson(json, mClazz);
                        sendSuccessListCallback(mListCallBack, result, params.get(pagetype_key));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sendFailedListCallback(mListCallBack, dataError, params.get(pagetype_key));
                }
            }
        });
    }

    public void function(String url, HashMap<String, String> params, final CallBack<Entity> callBack) {
        function(url, params, Entity.class, callBack);
    }

    public <T> void function(String url, HashMap<String, String> params, final Class<T> clazz,
                             final CallBack<T> callBack) {
        Request request = new Request.Builder()
                .url(url)
                .post(appendHeader(params))
                .tag(MyApplication.APP)
                .build();

        if (!Tools.checkNetworkState(MyApplication.APP)) {
            callBack.onFailure(noInternet);
            return;
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                sendFailedCallback(callBack, genericError);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    if (BuildConfig.DEBUG) Log.d("GsonRequest", json);
                    Entity entity = mGson.fromJson(json, Entity.class);
                    if (entity.getCode() == ACCOUNT_EXCEPTION) {
                        sendAlertCallback(MyApplication.APP, entity.getCode(), entity.getMessage());
                    } else {
                        T result = mGson.fromJson(json, clazz);
                        sendSuccessCallback(callBack, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sendFailedCallback(callBack, dataError);
                }
            }
        });
    }

}
