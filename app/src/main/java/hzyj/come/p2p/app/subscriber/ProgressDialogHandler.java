package hzyj.come.p2p.app.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * Created by Youga on 2016/3/30.
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private ProgressDialog mDialog;

    private final Context context;
    private final ProgressCancelListener mProgressCancelListener;
    private final String message;
    private boolean cancelable;


    public ProgressDialogHandler(@NonNull Context context,
                                 @NonNull ProgressCancelListener progressCancelListener,
                                 @NonNull String message,
                                 boolean cancelable) {
        super(Looper.getMainLooper());
        this.context = context;
        this.mProgressCancelListener = progressCancelListener;
        this.message = message;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(String message) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(context);
            mDialog.setMessage(message);
            mDialog.setCancelable(cancelable);
            if (cancelable) {
                mDialog.setOnCancelListener(dialogInterface -> mProgressCancelListener.onCancelProgress());
            }
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog(message);
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    public interface ProgressCancelListener {
        void onCancelProgress();
    }

}
