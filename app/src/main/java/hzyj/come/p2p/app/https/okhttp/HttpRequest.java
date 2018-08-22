package hzyj.come.p2p.app.https.okhttp;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;



import java.util.HashMap;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.ListCallBack;
import hzyj.come.p2p.app.https.config.CommonPreferences;
import hzyj.come.p2p.app.https.config.CommonRetrofit;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.subscriber.InterceptionSubscriber;
import hzyj.come.p2p.entity.EntitiyUser;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Youga on 2015/9/19.
 */
public abstract class HttpRequest implements NetWorkConstant {

    public static final String TYPE_LOGIN_OUT = "loginout";

    protected final String TAG = getClass().getSimpleName();
    protected final Handler mDelivery;
    protected final OkHttpClient mOkHttpClient;
    protected Gson mGson;
    protected String noInternet, genericError, dataError;


    protected HttpRequest() {
        mDelivery = CommonRetrofit.getDelivery();
        mOkHttpClient = CommonRetrofit.initOkHttpClient(MyApplication.APP);
        mGson = CommonRetrofit.getGson();
        noInternet = MyApplication.APP.getString(R.string.no_internet);
        genericError = MyApplication.APP.getString(R.string.generic_error);
        dataError = MyApplication.APP.getString(R.string.server_data_error);
    }

    protected FormBody.Builder paramsToFormBody(HashMap<String, String> params) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params == null) params = new HashMap<>();
        for (String key : params.keySet()) {
            formBuilder.add(key, params.get(key));
        }
        return formBuilder;
    }

    protected RequestBody appendHeader(HashMap<String, String> params) {
        FormBody.Builder formBuilder = paramsToFormBody(params);
//        formBuilder.add(token_key, token_value);
        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
        EntitiyUser user = preferences.getModel(EntitiyUser.class);
        if (user == null) return formBuilder.build();
        formBuilder.add(appUserId, user.getAppUserId());
//        formBuilder.add(uuid_key, employee.getUuid());
//        formBuilder.add(managerid_key, employee.getId());
//        formBuilder.add(groupid_key, employee.getGroupid());
//        formBuilder.add(eid_key, employee.getEid());
//        formBuilder.add(surperid_key, employee.getSuperid());
        return formBuilder.build();
    }

    protected RequestBody appendPageHeader(HashMap<String, String> params) {
        if (params == null) params = new HashMap<>();
        params.put(pagenumber_key, pagenumber_value);
        return appendHeader(params);
    }

    protected void sendFailedListCallback(ListCallBack callBack, String error, String type) {
        mDelivery.post(() -> callBack.onFailure(error, type));
    }

    protected void sendSuccessListCallback(ListCallBack callBack, Object result, String type) {
        mDelivery.post(() -> callBack.onResponse(result, type));
    }

    protected void sendFailedCallback(CallBack callBack, String error) {
        mDelivery.post(() -> callBack.onFailure(error));
    }

    protected void sendSuccessCallback(CallBack callBack, Object result) {
        mDelivery.post(() -> callBack.onResponse(result));
    }

    protected void sendAlertCallback(Context context, int code, String message) {
        mDelivery.post(() -> InterceptionSubscriber.onAccountException(context, code, message));
    }
}
