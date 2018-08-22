package hzyj.come.p2p.mvp.register;
import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;

import javax.inject.Inject;

import hzyj.come.p2p.app.cache.CommonCache;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.https.okhttp.GsonRequest;
import hzyj.come.p2p.app.service.UserService;
import hzyj.come.p2p.entity.EntitiyUser;
import hzyj.come.p2p.entity.Entity;
import hzyj.come.p2p.entity.EntityBase;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by EverGlow on 2018/8/10 12:04
 */
@ActivityScope
public    class RegisterModel extends BaseModel  implements RegisterContract.Model  {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
 
    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public EntityBase regester(String account, String pwd) {
        return  null;
    }
}
