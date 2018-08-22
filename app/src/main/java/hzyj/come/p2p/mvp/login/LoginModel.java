package hzyj.come.p2p.mvp.login;
import android.content.Intent;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.entity.EntityBase;
import hzyj.come.p2p.mvp.ui.activity.MainActivity;

/**
 * Created by EverGlow on 2018/8/14 14:23
 */
@ActivityScope
public    class LoginModel extends BaseModel  implements LoginContract.model  {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void login() {

    }
}
