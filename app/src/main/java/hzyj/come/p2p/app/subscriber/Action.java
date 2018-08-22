package hzyj.come.p2p.app.subscriber;

/**
 * Created by Youga on 2016/3/24.
 */
public interface Action<T> {
    void call(T t);
}
