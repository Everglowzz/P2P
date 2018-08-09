package hzyj.come.p2p.mvp.ui.fragment.homefragment;


import dagger.Module;
import dagger.Provides;
import hzyj.come.p2p.app.MainDataManager;

/**
 * Created by admin on 2017/3/12.
 */
@Module
public class HomePresenterModule {
    private HomeContract.View view;

    private MainDataManager mainDataManager;

    public HomePresenterModule(HomeContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    HomeContract.View providerMainContractView(){
        return view;
    }
    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
