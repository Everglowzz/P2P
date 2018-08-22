package hzyj.come.p2p.app.subscriber;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonSyntaxException;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import hzyj.come.p2p.R;
import retrofit2.HttpException;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class InterceptionSubscriber<T> implements ProgressDialogHandler.ProgressCancelListener, Subscriber<T> {

    private static final String TAG = "InterceptionSubscriber";
    private final Action<String> mOnError;
    private final Action<T> mOnNext;
    private final Context context;
    private ProgressDialogHandler mProgressHandler;
    private static final int SUCCESS = 1, ACCOUNT_EXCEPTION = 999;

    public InterceptionSubscriber(@NonNull Action<T> onNext,
                                  @NonNull Action<String> onError,
                                  @NonNull Context context) {
        this.mOnNext = onNext;
        this.mOnError = onError;
        this.context = context;
    }

    public InterceptionSubscriber(@NonNull Action<T> onNext,
                                  @NonNull Action<String> onError,
                                  @NonNull Context context,
                                  @NonNull String message,
                                  boolean cancelable) {
        this(onNext, onError, context);
        mProgressHandler = new ProgressDialogHandler(context, this, message, cancelable);
    }

    private void showProgressDialog() {
        if (mProgressHandler != null) {
            mProgressHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressHandler != null) {
            mProgressHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressHandler = null;
        }
    }

 
    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        onError(e, mOnError, context);
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    public static void onError(Throwable e, Action<String> mOnError, Context context) {
        if (e instanceof SocketTimeoutException) {
            mOnError.call(context.getResources().getString(R.string.server_connect_time_out));
        } else if (e instanceof ConnectException) {
            mOnError.call(context.getResources().getString(R.string.generic_error));
        } else if (e instanceof UnknownHostException) {
            mOnError.call(context.getResources().getString(R.string.no_internet));
        } else if (e instanceof HttpException) {
            mOnError.call(context.getResources().getString(R.string.http_exception));
        } else if (e instanceof IllegalArgumentException) {
            mOnError.call(e.getMessage());
        } else if (e instanceof JsonSyntaxException) {
            mOnError.call(e.getMessage());
        } else if (e instanceof IllegalStateException) {
            mOnError.call(e.getMessage());
        } else {
            mOnError.call(context.getResources().getString(R.string.server_data_error));
        }
        e.printStackTrace();
    }

    @Override
    public void onSubscribe(Subscription s) {
        showProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        Class<?> clazz = t.getClass();
        try {
            Field codeField = clazz.getDeclaredField("code");
            codeField.setAccessible(true);
            Field messageField = clazz.getDeclaredField("message");
            messageField.setAccessible(true);
            if (codeField.getInt(t) == SUCCESS) {
                mOnNext.call(t);
            } else if (codeField.getInt(t) == ACCOUNT_EXCEPTION) {
                mOnNext.call(t);
                onAccountException(context, codeField.getInt(t), messageField.get(t).toString());
//                Intent alertIntent = new Intent(context, AlertActivity.class);
//                alertIntent.putExtra(Config.NOTIFY, new Notify("账号异常", "你的账号已在其它设备登陆,请你重新登录"));
//                context.startActivity(alertIntent);
            } else {
                Log.i(TAG, "Thread:" + Thread.currentThread().getName());
                mOnError.call(messageField.get(t).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mOnNext.call(t);
        }
    }

    public static void onAccountException(Context context, int code, String message) {
        if (code != ACCOUNT_EXCEPTION) return;
//        Intent intent = new Intent(context, AlertActivity.class);
//        intent.putExtra(Config.NOTIFY, new Notify("账号异常", "你的账号已在其它设备登陆,请你重新登录"));
//        context.startActivity(intent);
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
//        if (mOnError != null)
//            mOnError.call("");
//        Log.i(TAG, context.getResources().getString(R.string.unsubscribe));
//        if (!this.isUnsubscribed()) this.unsubscribe();
    }
}