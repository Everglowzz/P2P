package hzyj.come.p2p.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import hzyj.come.p2p.di.module.LoginModule;
import hzyj.come.p2p.di.module.MainModule;
import hzyj.come.p2p.mvp.login.LoginActivity;

/**
 * Created by EverGlow on 2018/8/14 14:40
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public    interface LoginComponent   {

    void inject(LoginActivity loginActivity);
}
