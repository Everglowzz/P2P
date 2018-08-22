package hzyj.come.p2p.copy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.ToastUtil;


/**
 * Created by Youga on 2015/11/4.
 */
public class BaseDialog extends Dialog {
    Context mContext;
    public BaseDialog(Context context) {
        super(context, R.style.custom_dialog);
        mContext = context;
    }

    protected void showToast(String text) {
        if (text != null && !TextUtils.isEmpty(text.trim()) && !"null".equals(text.trim()))
            ToastUtil.showMessage(text);
    }

    protected void showToast(int text) {
        showToast(mContext.getString(text));
    }
}
