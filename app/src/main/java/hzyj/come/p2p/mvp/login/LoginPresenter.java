package hzyj.come.p2p.mvp.login;


import android.content.Intent;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.CommonPreferences;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.https.okhttp.GsonRequest;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.entity.EntityBase;
import hzyj.come.p2p.mvp.ui.activity.MainActivity;

/**
 * Created by EverGlow on 2018/8/14 14:25
 */
@ActivityScope
public    class LoginPresenter  extends BasePresenter<LoginContract.model,LoginContract.view> {

    @Inject
    GsonRequest mGsonRequest;
    @Inject
    public LoginPresenter(LoginContract.model model, LoginContract.view rootView) {
        super(model, rootView);
    }
    
    public void login(String account, String pwd) {

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_empty));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(NetWorkConstant.appUserAccount, account);
            map.put("appUserId","11");
            map.put(NetWorkConstant.appPassword, Tools.md5(Tools.md5(pwd) + "fht"));

            mGsonRequest.function(NetWorkConstant.app_login, map, EntityBase.class, new CallBack<EntityBase>() {
                @Override
                public void onResponse(EntityBase result) {
                    if (result.getResult() == 0) {
                        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
                        preferences.setModel(result.getAppUsers());
                        mRootView.launchActivity(new Intent(MyApplication.APP, MainActivity.class));
                        mRootView.killMyself();
                    }
                    mRootView.showMessage(result.getMsg());
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showMessage(error);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGsonRequest = null;
    }
}
