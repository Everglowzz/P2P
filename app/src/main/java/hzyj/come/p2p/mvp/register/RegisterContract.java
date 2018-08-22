package hzyj.come.p2p.mvp.register;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;



import hzyj.come.p2p.entity.EntityBase;
import io.reactivex.Observable;

/**
 * Created by EverGlow on 2018/8/10 11:57
 */

public interface RegisterContract {
    
    interface View extends IView{
    }
    interface Model extends IModel {
        EntityBase regester(String account, String pwd);
    }
}
