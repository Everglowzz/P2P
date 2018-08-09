package hzyj.come.p2p.mvp.ui.fragment.homefragment;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerFragment;

import dagger.Component;

/**
 * Created by admin on 2017/3/12.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = HomePresenterModule.class)
public interface HomeFragmentComponent {
    void inject(MainHomeFragment fragment);
}
