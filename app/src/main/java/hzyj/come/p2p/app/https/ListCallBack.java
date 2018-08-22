package hzyj.come.p2p.app.https;

/**
 * Created by Youga on 2015/8/25.
 */
public interface ListCallBack<T> {

    void onResponse(T result, String type);

    void onFailure(String error, String type);
}
