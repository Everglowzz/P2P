package hzyj.come.p2p.mvp.login;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * Created by EverGlow on 2018/8/14 14:21
 */

public interface LoginContract {

    interface view extends IView {

    }

    interface model extends IModel {
        void login();
    }

}
