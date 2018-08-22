package hzyj.come.p2p.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import hzyj.come.p2p.di.module.RegisterModule;
import hzyj.come.p2p.mvp.register.RegisterActivity;

/**
 * Created by EverGlow on 2018/8/10 15:46
 */
@ActivityScope 
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public    interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
}
