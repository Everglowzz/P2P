package hzyj.come.p2p.mvp.gate;

import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.config.CommonPreferences;
import hzyj.come.p2p.entity.EntitiyUser;

/**
 * Created by EverGlow on 2018/8/14 15:31
 */

public    class GatePresenter  implements GateContract.presenter  {
    
    private GateContract.view mView;

    public GatePresenter(GateContract.view view) {
        mView = view;
    }

    @Override
    public void judge() {
        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
        EntitiyUser user = preferences.getModel(EntitiyUser.class);
        if (user == null) {
            mView.skipLogin();
        }else{
            mView.skipMain();
        }
    }
}
