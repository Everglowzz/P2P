package hzyj.come.p2p.mvp.register;

import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.https.okhttp.GsonRequest;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.entity.EntityBase;

/**
 * Created by EverGlow on 2018/8/10 12:00
 */
@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {


    @Inject
    GsonRequest mGsonRequest;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView) {
        super(model, rootView);

    }


    public void regester(String account, String passWord, String pwd2) {

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(passWord) || TextUtils.isEmpty(pwd2)) {
            mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_empty));
        } else if (!account.matches("^[a-zA-Z0-9]{6,11}$")) {
            mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_illegality1));
        } else if (!passWord.matches("^(?=.*[0-9].*)(?=.*[A-Za-z].*).{6,20}$")) {
            mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_illegality2));
        } else if (!passWord.equals(pwd2)) {
            mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_illegality3));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(NetWorkConstant.appUserAccount, account);
            map.put(NetWorkConstant.appPassword, Tools.md5(Tools.md5(pwd2) + "fht"));

            mGsonRequest.function(NetWorkConstant.app_user_regist, map, EntityBase.class, new CallBack<EntityBase>() {
                @Override
                public void onResponse(EntityBase result) {
                    if (result.getResult() == 0) {
                        mRootView.killMyself();
                    }
                    mRootView.showMessage(result.getMsg());
                }

                @Override
                public void onFailure(String error) {
                    mRootView.showMessage(error);
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
