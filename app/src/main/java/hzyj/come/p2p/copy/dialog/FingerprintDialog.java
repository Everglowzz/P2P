package hzyj.come.p2p.copy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hzyj.come.p2p.R;
import hzyj.come.p2p.copy.BaseActivity;

/**
 * Created by EverGlow on 2018/8/29 10:36
 */

public class FingerprintDialog extends BaseDialog {

    private String tip;
    private Context mContext;
    private TextView mTvTip;

    public FingerprintDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }
    

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_fingerprint_layout, null);
        TextView tvCancel = view.findViewById(R.id.cancel);
        mTvTip = view.findViewById(R.id.tv_tips);
        
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tvCancel.setOnClickListener(v -> dismiss());
    }

    public void setTip(String tip) {
        if (mTvTip != null) {
            mTvTip.setText(tip);
        }
    }

}
