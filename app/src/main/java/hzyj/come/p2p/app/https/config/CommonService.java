package hzyj.come.p2p.app.https.config;


import java.util.HashMap;

import hzyj.come.p2p.entity.EntitiyUser;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Youga on 2016/3/21.
 */
public interface CommonService {



   
    @FormUrlEncoded
    @POST(NetWorkConstant.app_user_regist)
    Observable<EntitiyUser> register(@FieldMap HashMap<String, String> params);
}
