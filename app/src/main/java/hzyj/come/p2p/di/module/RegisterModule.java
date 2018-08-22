package hzyj.come.p2p.di.module;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import hzyj.come.p2p.app.https.okhttp.GsonRequest;
import hzyj.come.p2p.mvp.register.RegisterContract;
import hzyj.come.p2p.mvp.register.RegisterModel;

/**
 * Created by EverGlow on 2018/8/10 15:45
 */

@Module
public    class RegisterModule {
    
    RegisterContract.View mView;
 
    public RegisterModule(RegisterContract.View view) {
        mView = view;
    }
    
    @ActivityScope
    @Provides
    RegisterContract.View provideRegisterView(){
        return mView;
    }
    
    @ActivityScope
    @Provides
    RegisterContract.Model provideRegisterModel(RegisterModel model){
        return  model;
    }
    
    
  
}
