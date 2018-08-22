package hzyj.come.p2p.mvp.gate;

import android.content.Intent;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IView;

/**
 * Created by EverGlow on 2018/8/14 15:27
 */

public  interface  GateContract     {
    
    interface view {
        void skipMain();
        void skipLogin();
    }
    
    interface presenter {
        void judge();
    }
}
